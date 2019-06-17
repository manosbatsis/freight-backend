package com.freight.exception;

/**
 * Created by toshikijahja on 3/26/19.
 */
public enum BadRequest implements ResponseError {
    ACCOUNT_WITH_EMAIL_EXIST("Account with this email already exist"),
    ACCOUNT_WITH_PHONE_EXIST("Account with this phone number already exist"),
    EMAIL_PHONE_EMPTY("Email and phone number cannot be both empty"),
    INVALID_COOKIE("Cookie is invalid"),
    VERIFICATION_CODE_WRONG("Verification code is wrong");

    private final String errorDescription;

    BadRequest(final String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public int getHttpResponseCode() {
        return 400;
    }

    public String getErrorKey() {
        return name();
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}

