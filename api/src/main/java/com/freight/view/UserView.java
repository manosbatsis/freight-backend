package com.freight.view;

import com.freight.model.User;

import java.time.Instant;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class UserView {

    private final User user;

    public UserView(final User user) {
        this.user = user;
    }

    public int getId() {
        return user.getId();
    }

    public User.Status getStatus() {
        return user.getStatus();
    }

    public Instant getCreated() {
        return user.getCreated();
    }

    public Instant getLastModified() {
        return user.getLastModified();
    }

}
