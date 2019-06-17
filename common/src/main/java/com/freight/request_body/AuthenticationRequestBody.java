package com.freight.request_body;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class AuthenticationRequestBody implements Serializable {

    private String email;
    private Integer phone;
    @NotNull
    private String password;

    public Optional<String> getEmailOptional() {
        return ofNullable(this.email);
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Optional<Integer> getPhoneOptional() {
        return ofNullable(this.phone);
    }

    public void setPhone(final Integer phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
