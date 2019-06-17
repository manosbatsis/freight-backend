package com.freight.dao;

import com.freight.exception.FreightException;
import com.freight.model.Authentication;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import static com.freight.auth.JWTUtil.createJWT;
import static com.freight.exception.BadRequest.EMAIL_PHONE_EMPTY;
import static com.freight.exception.BadRequest.GUID_NOT_EXIST;
import static com.freight.exception.BadRequest.STATUS_NOT_EXIST;
import static com.freight.exception.BadRequest.VERIFICATION_CODE_NOT_EXIST;
import static com.freight.exception.BadRequest.VERIFICATION_CODE_WRONG;
import static com.freight.exception.Unauthorized.UNAUTHORIZED;
import static com.freight.model.Authentication.Status.UNVERIFIED;
import static com.freight.model.User.Type.NOT_KNOWN;
import static com.freight.util.AssertUtil.assertNotNull;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.util.Objects.requireNonNull;
import static org.mindrot.jbcrypt.BCrypt.hashpw;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class AuthenticationDao extends BaseDao<Authentication> {

    @AssistedInject
    public AuthenticationDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, Authentication.class);
    }

    public Optional<Authentication> getByGuid(final String guid) {
        requireNonNull(guid);
        return getFirst(getByField("guid", guid));
    }

    public Optional<Authentication> getByEmail(final String email) {
        requireNonNull(email);
        return getFirst(getByField("email", email));
    }

    public Optional<Authentication> getByPhone(final Integer phone) {
        requireNonNull(phone);
        return getFirst(getByField("phone", phone));
    }

    /**
     * Either emailOptional or phoneOptional has to present
     * @param emailOptional can be empty
     * @param phoneOptional can be empty
     * @param password
     * @return access token
     */
    public String createAuthentication(final Optional<String> emailOptional,
                                       final Optional<Integer> phoneOptional,
                                       final String password) {
        requireNonNull(emailOptional);
        requireNonNull(phoneOptional);
        requireNonNull(password);
        getSessionProvider().startTransaction();
        final String pass = hashpw(password, BCrypt.gensalt(12));
        System.out.println("password:" + pass);
        final String verificationCode = generateVerificationToken();
        final Authentication authentication = new Authentication.Builder()
                .email(emailOptional.orElse(null))
                .phone(phoneOptional.orElse(null))
                .password(pass)
                .status(UNVERIFIED)
                .verificationCode(verificationCode)
                .verificationExpiry(Instant.now().plus(1, HOURS))
                .build();
        getSessionProvider().getSession().persist(authentication);
        getSessionProvider().commitTransaction();

        return createJWT(null, authentication.getGuid(), NOT_KNOWN, UNVERIFIED, authentication.getToken());
    }

    private String generateVerificationToken() {
        final Random rand = new Random();
        return String.format("%04d%n", rand.nextInt(10000));
    }

    /**
     * Either emailOptional or phoneOptional has to present.
     * It will check if password is correct.
     * @param emailOptional can be empty
     * @param phoneOptional can be empty
     * @param password
     * @return accessToken
     */
    public String authenticate(final Optional<String> emailOptional,
                               final Optional<Integer> phoneOptional,
                               final String password) {
        final Optional<Authentication> authenticationOptional;
        if (emailOptional.isPresent()) {
            authenticationOptional = getByEmail(emailOptional.get());
        } else if (phoneOptional.isPresent()) {
            authenticationOptional = getByPhone(phoneOptional.get());
        } else {
            throw new FreightException(EMAIL_PHONE_EMPTY);
        }

        if (!authenticationOptional.isPresent() || password == null) {
            throw new FreightException(UNAUTHORIZED);
        }

        if (!Objects.equals(authenticationOptional.get().getPassword(), password)) {
            throw new FreightException(UNAUTHORIZED);
        }

        return createJWT(null, authenticationOptional.get().getGuid(), NOT_KNOWN, UNVERIFIED,
                authenticationOptional.get().getToken());
    }

    public Authentication verifyCode(final String guid, final String verificationCode) {
        assertNotNull(guid, GUID_NOT_EXIST);
        assertNotNull(verificationCode, VERIFICATION_CODE_NOT_EXIST);
        final Optional<Authentication> authenticationOptional = getByGuid(guid);
        if (!authenticationOptional.isPresent()) {
            throw new FreightException(UNAUTHORIZED);
        }

        final Authentication authentication = authenticationOptional.get();
        if (!Objects.equals(authentication.getVerificationCode(), verificationCode)) {
            throw new FreightException(VERIFICATION_CODE_WRONG);
        }

        return authentication;
    }

    public void setStatus(final String guid, final Authentication.Status status) {
        assertNotNull(guid, GUID_NOT_EXIST);
        assertNotNull(status, STATUS_NOT_EXIST);
        final Query query = getSessionProvider().getSession().createQuery(
                "UPDATE " + clazz.getName() + " SET status = :status WHERE guid = :guid");
        query.setParameter("status", status);
        query.setParameter("guid", guid);
        query.executeUpdate();
    }
}
