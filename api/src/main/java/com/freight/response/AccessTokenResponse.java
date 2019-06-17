package com.freight.response;

/**
 * Created by toshikijahja on 10/18/17.
 */
public class AccessTokenResponse extends BaseResponse {

    private final String token;

    public AccessTokenResponse(final String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
