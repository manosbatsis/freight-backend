package com.freight.exception;

/**
 * Created by toshikijahja on 3/26/19.
 */
public enum BadRequest implements ResponseError {
    ACCOUNT_WITH_EMAIL_EXIST("Account with this email already exist"),
    ACCOUNT_WITH_PHONE_EXIST("Account with this phone number already exist"),
    CARGO_TYPE_NOT_EXIST("Cargo type does not exist"),
    COMPANY_NAME_NOT_EXIST("Company name does not exist"),
    COMPANY_NOT_EXIST("Company does not exist"),
    DESTINATION_ORIGIN_PORT_CANNOT_BE_SAME("Destination and origin port cannot be the same"),
    DESTINATION_PORT_EMPTY("Destination port cannot be empty"),
    EMAIL_PHONE_EMPTY("Email and phone number cannot be both empty"),
    ESTIMATED_ARRIVAL_IN_PAST("Estimated arrival time has to be in future"),
    ESTIMATED_DEPARTURE_IN_PAST("Estimated departure time has to be in future"),
    ESTIMATED_DEPARTURE_NOT_BEFORE_ESTIMATED_ARRIVAL("Estimated departure has to be before estimated arrival time"),
    GUID_NOT_EXIST("Guid does not exist"),
    ORIGIN_PORT_EMPTY("Origin port cannot be empty"),
    PORT_NOT_EXIST("Port does not exist"),
    SHIP_NAME_NOT_EXIST("Ship name does not exist"),
    SHIP_NOT_EXIST("Ship does not exist"),
    STATUS_NOT_EXIST("Status does not exist"),
    TYPE_NOT_EXIST("Type does not exist"),
    USER_NOT_EXIST("User does not exist"),
    VERIFICATION_CODE_NOT_EXIST("Verification code does not exist"),
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

