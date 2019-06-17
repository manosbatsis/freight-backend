package com.freight.auth;

import com.freight.exception.FreightException;
import com.google.inject.Provider;
import io.jsonwebtoken.Claims;
import org.glassfish.jersey.server.ExtendedUriInfo;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

import static com.freight.auth.JWTUtil.AUTHENTICATION_STATUS;
import static com.freight.auth.JWTUtil.GUID;
import static com.freight.auth.JWTUtil.USER_TYPE;
import static com.freight.auth.JWTUtil.parseJWT;
import static com.freight.exception.Unauthorized.UNAUTHORIZED;
import static javax.ws.rs.Priorities.AUTHENTICATION;

/**
 * Created by toshikijahja on 3/26/19.
 */
@Priority(AUTHENTICATION)
public class UserFilter implements ContainerRequestFilter {

    public static final String AUTHENTICATION_SCHEME = "Bearer";

    @Context
    private Provider<UserScope> userScopeProvider;

    @Context
    private ExtendedUriInfo extendedUriInfo;

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        final UserAuth annotation = extendedUriInfo.getMatchedResourceMethod()
                .getInvocable()
                .getHandlingMethod()
                .getAnnotation(UserAuth.class);

        final boolean isTokenOptional = annotation.optional();

        try {
            // Get the Authorization header from the request
            final String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

            // Validate the Authorization header
            if (!isTokenBasedAuthentication(authorizationHeader)) {
                throw new FreightException(UNAUTHORIZED);
            }

            // Extract the token from the Authorization header
            final String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

            final Claims claims = parseJWT(token);
            userScopeProvider.get().setGuid((String) claims.get(GUID));
            userScopeProvider.get().setUserType((String) claims.get(USER_TYPE));
            userScopeProvider.get().setAuthenticationStatus((String) claims.get(AUTHENTICATION_STATUS));

        } catch (final RuntimeException exception) {
            // Only throw exception if token is not optional
            if(!isTokenOptional) {
                throw exception;
            }
        }
    }

    /**
     * Check if the Authorization header is valid
     * It must not be null and must be prefixed with "Bearer" plus a whitespace
     * The authentication scheme comparison must be case-insensitive
     * @param authorizationHeader
     * @return
     */
    private boolean isTokenBasedAuthentication(final String authorizationHeader) {
        return authorizationHeader != null &&
                authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }
}
