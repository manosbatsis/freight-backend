package com.freight.response;

import com.freight.model.Type;

/**
 * Created by toshikijahja on 10/18/17.
 */
public class AccessTokenResponse extends BaseResponse {

    private final String token;
    private final Type type;

    public AccessTokenResponse(final String token,
                               final Type type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public Type getType() {
        return type;
    }
}
