package com.freight.exception;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class FreightException extends RuntimeException {

    private final ResponseError responseError;

    public FreightException(final ResponseError responseError) {
        super(responseError.getErrorDescription());
        this.responseError = responseError;
    }

    public ResponseError getResponseError() {
        return responseError;
    }
}
