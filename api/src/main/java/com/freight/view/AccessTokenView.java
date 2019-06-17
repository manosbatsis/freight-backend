package com.freight.view;

/**
 * Created by toshikijahja on 10/18/17.
 */
public class AccessTokenView extends BaseView {

    private final String token;

    public AccessTokenView(final String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
