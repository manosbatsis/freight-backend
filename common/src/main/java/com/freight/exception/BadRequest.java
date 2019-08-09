package com.freight.exception;

/**
 * Created by toshikijahja on 3/26/19.
 */
public enum BadRequest implements ResponseError {
    ACCOUNT_WITH_EMAIL_EXIST("Account with this email already exist"),
    ACCOUNT_WITH_PHONE_EXIST("Account with this phone number already exist"),
    BULK_INPUT_EMPTY("Input needed for Bulk cargo type cannot be empty"),
    BULK_TYPE_NOT_EXIST("Bulk type does not exist"),
    CARGO_NOT_EXIST("Cargo does not exist"),
    CARGO_STATUS_NOT_EXIST("Cargo status does not exist"),
    CARGO_TYPE_NOT_EXIST("Cargo type does not exist"),
    COMPANY_NAME_NOT_EXIST("Company name does not exist"),
    COMPANY_NOT_EXIST("Company does not exist"),
    CONTAINER_TYPE_NOT_EXIST("Container type does not exist"),
    DESTINATION_ORIGIN_PORT_CANNOT_BE_SAME("Destination and origin port cannot be the same"),
    DESTINATION_PORT_EMPTY("Destination port cannot be empty"),
    DIMENSION_EMPTY("Dimension unit cannot be empty"),
    EMAIL_PHONE_EMPTY("Email and phone number cannot be both empty"),
    ARRIVAL_IN_PAST("Arrival date has to be in future"),
    DEPARTURE_IN_PAST("Departure date has to be in future"),
    DEPARTURE_NOT_BEFORE_ARRIVAL("Departure date has to be before arrival date"),
    FCL_INPUT_EMPTY("Input needed for FCL cargo type cannot be empty"),
    GUID_NOT_EXIST("Guid does not exist"),
    LCL_INPUT_EMPTY("Input needed for LCL cargo type cannot be empty"),
    ORIGIN_PORT_EMPTY("Origin port cannot be empty"),
    PAYOUT_NOT_SUM_UP("Payment schedule has to sum up to 100%"),
    PORT_NOT_EXIST("Location does not exist"),
    SHIP_CARGO_TYPE_NOT_MATCH("Ship cargo type does not match cargo type"),
    SHIP_NAME_NOT_EXIST("Ship name does not exist"),
    SHIP_NOT_EXIST("Ship does not exist"),
    SHIPMENT_NOT_EXIST("Shipment does not exist"),
    SHIPMENT_STATUS_NOT_EXIST("Shipment status does not exist"),
    STATUS_NOT_EXIST("Status does not exist"),
    TYPE_NOT_EXIST("Type does not exist"),
    USER_NOT_EXIST("User does not exist"),
    VERIFICATION_CODE_NOT_EXIST("Verification code does not exist"),
    VERIFICATION_CODE_WRONG("Verification code is wrong"),
    VOLUME_EMPTY("Volume unit cannot be empty"),
    WEIGHT_EMPTY("Weight unit cannot be empty");

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

