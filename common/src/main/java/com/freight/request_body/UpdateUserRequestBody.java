package com.freight.request_body;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class UpdateUserRequestBody implements Serializable {

    private String username;

    public Optional<String> getUsernameOptional() {
        return Optional.ofNullable(this.username);
    }

    public void setAlias(final String username) {
        this.username = username;
    }
}
