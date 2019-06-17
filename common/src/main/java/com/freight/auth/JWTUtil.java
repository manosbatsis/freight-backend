package com.freight.auth;

import com.freight.exception.FreightException;
import com.freight.model.Authentication;
import com.freight.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.util.Objects;

import static com.freight.exception.Unauthorized.UNAUTHORIZED;
import static java.time.temporal.ChronoUnit.HOURS;

/**
 * Created by toshikijahja on 10/23/18.
 */
public class JWTUtil {

    private static final String KEY = "freight-token-sign-key";
    private static final String ISSUER = "freight-backend";
    public static final String GUID = "guid";
    public static final String USER_TYPE = "user_type";
    public static final String AUTHENTICATION_STATUS = "authentication_status";
    public static final String TOKEN  = "token";

    public static String createJWT(final String username,
                                   final String guid,
                                   final User.Type type,
                                   final Authentication.Status status,
                                   final String token) {
        return createJWT(username, guid, type, status, token, null);
    }

    public static String createJWT(final String username,
                                   final String guid,
                                   final User.Type type,
                                   final Authentication.Status status,
                                   final String token,
                                   final Integer expirationInHours) {
        final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        final byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(KEY);
        final Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        final Instant now = Instant.now();
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(GUID, guid);
        claims.put(USER_TYPE, type.name());
        claims.put(AUTHENTICATION_STATUS, status.name());
        claims.put(TOKEN, token);

        // TODO: set expiration
        final JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setIssuer(ISSUER)
                .setIssuedAt(Date.from(now))
                .signWith(signatureAlgorithm, signingKey);

        if (expirationInHours != null) {
            builder.setExpiration(Date.from(now.plus(expirationInHours, HOURS)));
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static Claims parseJWT(final String jwt) {
        final Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(KEY))
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (final JwtException exception) {
            throw new FreightException(UNAUTHORIZED);
        }

        if (!Objects.equals(claims.getIssuer(), ISSUER)) {
            throw new FreightException(UNAUTHORIZED);
        }
        return claims;
    }
}
