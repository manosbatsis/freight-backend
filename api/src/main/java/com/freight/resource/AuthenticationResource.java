package com.freight.resource;

import com.freight.auth.UserAuth;
import com.freight.auth.UserScope;
import com.freight.dao.AuthenticationDao;
import com.freight.dao.SessionProvider;
import com.freight.dao.UserDao;
import com.freight.exception.FreightException;
import com.freight.model.Authentication;
import com.freight.persistence.DaoProvider;
import com.freight.request_body.AuthenticationRequestBody;
import com.freight.request_body.AuthenticationVerificationRequestBody;
import com.freight.response.AccessTokenResponse;
import com.google.inject.Provider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static com.freight.auth.JWTUtil.createJWT;
import static com.freight.exception.BadRequest.ACCOUNT_WITH_EMAIL_EXIST;
import static com.freight.exception.BadRequest.ACCOUNT_WITH_PHONE_EXIST;
import static com.freight.exception.BadRequest.EMAIL_PHONE_EMPTY;
import static com.freight.exception.BadRequest.VERIFICATION_CODE_NOT_EXIST;
import static com.freight.exception.Unauthorized.UNAUTHORIZED;
import static com.freight.model.Authentication.Status.UNVERIFIED;
import static com.freight.model.Authentication.Status.VERIFIED;
import static com.freight.model.User.Type.NOT_KNOWN;
import static com.freight.util.AssertUtil.assertNotNull;

/**
 * Created by toshikijahja on 6/14/19.
 */
@Api(tags = {"public"})
@Path("/authentication")
public class AuthenticationResource {

    @Inject
    private DaoProvider daoProvider;
    @Inject
    private Provider<UserScope> userScopeProvider;

    @POST
    @ApiOperation(value = "Sign user up")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public AccessTokenResponse signUp(final AuthenticationRequestBody authenticationRequestBody) throws Exception {
        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final AuthenticationDao authenticationDao = daoProvider.getDaoFactory().getAuthenticationDao(sessionProvider);
            validateSignUpRequest(authenticationRequestBody, authenticationDao);

            final String accessToken = authenticationDao.createAuthentication(
                    authenticationRequestBody.getEmailOptional(),
                    authenticationRequestBody.getPhoneOptional(),
                    authenticationRequestBody.getPassword());

            // TODO: send verification code to email or phone

            return new AccessTokenResponse(accessToken);
        }
    }

    /**
     * Validate to make sure there are no accounts with given email/phone number exist already,
     * throws ACCOUNT_WITH_EMAIL_EXIST / ACCOUNT_WITH_PHONE_EXIST otherwise
     */
    private void validateSignUpRequest(final AuthenticationRequestBody authenticationRequestBody,
                                       final AuthenticationDao authenticationDao) {
        // Sign up with email
        if (authenticationRequestBody.getEmailOptional().isPresent()) {
            // check if email has already being taken
            authenticationDao.getByEmail(authenticationRequestBody.getEmailOptional().get())
                    .ifPresent(authentication -> {
                        throw new FreightException(ACCOUNT_WITH_EMAIL_EXIST);
                    });

        // Sign up with phone number
        } else if (authenticationRequestBody.getPhoneOptional().isPresent()) {
            // check if phone has already being taken
            authenticationDao.getByPhone(authenticationRequestBody.getPhoneOptional().get())
                    .ifPresent(authentication -> {
                        throw new FreightException(ACCOUNT_WITH_PHONE_EXIST);
                    });

        // Both email and phone number empty
        } else {
            throw new FreightException(EMAIL_PHONE_EMPTY);
        }
    }

    @POST
    @ApiOperation(value = "Authenticate user")
    @Path("/authenticate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public AccessTokenResponse signIn(final AuthenticationRequestBody authenticationRequestBody) throws Exception {
        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final AuthenticationDao authenticationDao = daoProvider.getDaoFactory().getAuthenticationDao(sessionProvider);
            final String accessToken = authenticationDao.authenticate(
                    authenticationRequestBody.getEmailOptional(),
                    authenticationRequestBody.getPhoneOptional(),
                    authenticationRequestBody.getPassword());

            return new AccessTokenResponse(accessToken);
        }
    }

    @POST
    @ApiOperation(value = "Verify user's verification code")
    @Path("/verify")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @UserAuth(optional = false)
    public AccessTokenResponse verifyCode(final AuthenticationVerificationRequestBody authenticationVerificationRequestBody)
            throws Exception {
        assertNotNull(authenticationVerificationRequestBody.getVerificationCode(), VERIFICATION_CODE_NOT_EXIST);

        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final AuthenticationDao authenticationDao = daoProvider.getDaoFactory().getAuthenticationDao(sessionProvider);
            final UserDao userDao = new UserDao(sessionProvider);

            final Authentication authentication = authenticationDao.getByGuid(
                    userScopeProvider.get().getGuid()).orElseThrow(() -> new FreightException(UNAUTHORIZED));

            if (authentication.getStatus() == UNVERIFIED) {
                authenticationDao.verifyCode(authentication.getGuid(),
                        authenticationVerificationRequestBody.getVerificationCode());

                sessionProvider.startTransaction();
                authenticationDao.setStatus(authentication.getGuid(), VERIFIED);
                userDao.createUser(authentication.getGuid(), authentication.getEmail(),
                        authentication.getPhone(), NOT_KNOWN);
                sessionProvider.commitTransaction();
            }

            final String accessToken = createJWT(null, authentication.getGuid(),
                    NOT_KNOWN, VERIFIED, authentication.getToken());
            return new AccessTokenResponse(accessToken);
        }
    }
}
