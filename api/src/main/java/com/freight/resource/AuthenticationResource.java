package com.freight.resource;

import com.freight.auth.UserAuth;
import com.freight.auth.UserScope;
import com.freight.dao.AuthenticationDao;
import com.freight.dao.CompanyDao;
import com.freight.dao.SessionProvider;
import com.freight.dao.UserDao;
import com.freight.exception.FreightException;
import com.freight.model.Authentication;
import com.freight.model.Company;
import com.freight.model.User;
import com.freight.persistence.DaoProvider;
import com.freight.request_body.AuthenticationRequestBody;
import com.freight.request_body.AuthenticationSignUpRequestBody;
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
import java.util.UUID;

import static com.freight.auth.JWTUtil.createJWT;
import static com.freight.exception.BadRequest.ACCOUNT_WITH_EMAIL_EXIST;
import static com.freight.exception.BadRequest.ACCOUNT_WITH_PHONE_EXIST;
import static com.freight.exception.BadRequest.EMAIL_PHONE_EMPTY;
import static com.freight.exception.BadRequest.TYPE_EMPTY;
import static com.freight.exception.BadRequest.VERIFICATION_CODE_NOT_EXIST;
import static com.freight.exception.Unauthorized.UNAUTHORIZED;
import static com.freight.model.Authentication.Status.UNVERIFIED;
import static com.freight.model.Authentication.Status.VERIFIED;
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
    public AccessTokenResponse signUp(final AuthenticationSignUpRequestBody authenticationSignUpRequestBody)
            throws Exception {
        try (final SessionProvider sessionProvider = daoProvider.getSessionProvider()) {
            final AuthenticationDao authenticationDao = daoProvider.getDaoFactory().getAuthenticationDao(sessionProvider);
            final UserDao userDao = daoProvider.getDaoFactory().getUserDao(sessionProvider);
            validateSignUpRequest(authenticationSignUpRequestBody, authenticationDao);

            sessionProvider.startTransaction();
            final String guid = String.valueOf(UUID.randomUUID());
            final String accessToken = authenticationDao.createAuthentication(
                    guid,
                    authenticationSignUpRequestBody.getEmailOptional(),
                    authenticationSignUpRequestBody.getPhoneOptional(),
                    authenticationSignUpRequestBody.getPassword(),
                    authenticationSignUpRequestBody.getType());

            final User user = userDao.createUser(
                    guid,
                    authenticationSignUpRequestBody.getEmailOptional().orElse(null),
                    authenticationSignUpRequestBody.getPhoneOptional().orElse(null),
                    authenticationSignUpRequestBody.getType());

            authenticationSignUpRequestBody.getCompanyNameOptional()
                    .ifPresent(companyName -> {
                        final CompanyDao companyDao = daoProvider.getDaoFactory().getCompanyDao(sessionProvider);
                        final Company company = companyDao.createCompany(companyName, authenticationSignUpRequestBody.getType());
                        userDao.updateCompany(user.getGuid(), company.getId());
                    });
            sessionProvider.commitTransaction();

            // TODO: send verification code to email or phone

            return new AccessTokenResponse(accessToken, user.getType());
        }
    }

    /**
     * Validate to make sure there are no accounts with given email/phone number exist already,
     * throws ACCOUNT_WITH_EMAIL_EXIST / ACCOUNT_WITH_PHONE_EXIST otherwise
     */
    private void validateSignUpRequest(final AuthenticationSignUpRequestBody authenticationSignUpRequestBody,
                                       final AuthenticationDao authenticationDao) {
        // Sign up with email
        if (authenticationSignUpRequestBody.getEmailOptional().isPresent()) {
            // check if email has already being taken
            authenticationDao.getByEmail(authenticationSignUpRequestBody.getEmailOptional().get())
                    .ifPresent(authentication -> {
                        throw new FreightException(ACCOUNT_WITH_EMAIL_EXIST);
                    });

        // Sign up with phone number
        } else if (authenticationSignUpRequestBody.getPhoneOptional().isPresent()) {
            // check if phone has already being taken
            authenticationDao.getByPhone(authenticationSignUpRequestBody.getPhoneOptional().get())
                    .ifPresent(authentication -> {
                        throw new FreightException(ACCOUNT_WITH_PHONE_EXIST);
                    });

        // Both email and phone number empty
        } else {
            throw new FreightException(EMAIL_PHONE_EMPTY);
        }

        if (authenticationSignUpRequestBody.getType() == null) {
            throw new FreightException(TYPE_EMPTY);
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
            final Authentication authentication = authenticationDao.authenticate(
                    authenticationRequestBody.getEmailOptional(),
                    authenticationRequestBody.getPhoneOptional(),
                    authenticationRequestBody.getPassword());
            final String accessToken = createJWT(
                    null,
                    authentication.getGuid(),
                    authentication.getType(),
                    UNVERIFIED,
                    authentication.getToken());

            return new AccessTokenResponse(accessToken, authentication.getType());
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

            final Authentication authentication = authenticationDao.getByGuid(
                    userScopeProvider.get().getGuid()).orElseThrow(() -> new FreightException(UNAUTHORIZED));

            if (authentication.getStatus() == UNVERIFIED) {
                authenticationDao.verifyCode(authentication.getGuid(),
                        authenticationVerificationRequestBody.getVerificationCode());

                sessionProvider.startTransaction();
                authenticationDao.setStatus(authentication.getGuid(), VERIFIED);
                sessionProvider.commitTransaction();
            }

            final String accessToken = createJWT(null, authentication.getGuid(),
                    authentication.getType(), VERIFIED, authentication.getToken());
            return new AccessTokenResponse(accessToken, authentication.getType());
        }
    }
}
