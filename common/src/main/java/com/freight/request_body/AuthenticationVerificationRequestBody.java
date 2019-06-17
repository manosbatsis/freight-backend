package com.freight.request_body;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by toshikijahja on 11/5/17.
 */
@ApiModel
public class AuthenticationVerificationRequestBody implements Serializable {

    @ApiModelProperty(value = "Verification code sent to user")
    private String verificationCode;

    public String getVerificationCode() {
        return this.verificationCode;
    }
}
