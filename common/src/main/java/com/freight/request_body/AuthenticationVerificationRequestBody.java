package com.freight.request_body;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by toshikijahja on 11/5/17.
 */
public class AuthenticationVerificationRequestBody implements Serializable {

    @NotNull
    private String verificationCode;

    public String getVerificationCode() {
        return this.verificationCode;
    }
}
