package com.freight.auth;

import com.freight.exception.FreightException;
import com.freight.model.Authentication;
import com.freight.model.Type;

import java.util.Optional;

import static com.freight.exception.Unauthorized.UNAUTHORIZED;

/**
 * Created by toshikijahja on 6/16/19.
 */
public class UserScope {

    private Optional<String> guidOptional = Optional.empty();
    private Type userType;
    private Authentication.Status authenticationStatus;

    public boolean isLoggedIn() {
        return guidOptional.isPresent();
    }

    public String getGuid() {
        return guidOptional.orElseThrow(() -> new FreightException(UNAUTHORIZED));
    }

    public void setGuid(final String guid) {
        this.guidOptional = Optional.ofNullable(guid);
    }

    public Type setUserType() {
        return userType;
    }

    public void setUserType(final String userType) {
        this.userType = Type.valueOf(userType);
    }

    public Authentication.Status getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(final String authenticationStatus) {
        this.authenticationStatus = Authentication.Status.valueOf(authenticationStatus);
    }
}
