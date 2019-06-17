package com.freight.exception;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class FreightError {
    private int code;
    private String errorKey;
    private String description;

    public int getCode() {
        return code;
    }

    public FreightError setCode(int code) {
        this.code = code;
        return this;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public FreightError setErrorKey(final String errorKey) {
        this.errorKey = errorKey;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FreightError setDescription(String description) {
        this.description = description;
        return this;
    }
}
