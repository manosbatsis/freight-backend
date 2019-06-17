package com.freight.response;


import com.freight.view.UserView;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class UserResponse extends BaseResponse {

    private final UserView user;

    public UserResponse(final UserView user) {
        this.user = user;
    }

    public UserView getUser() {
        return user;
    }
}
