package com.freight.view;

import com.freight.exception.FreightError;

import java.time.Instant;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class BaseView {
    private FreightError error;
    private long currentTime;

    public BaseView() {
        currentTime = Instant.now().toEpochMilli();
    }

    public FreightError getError() {
        return error;
    }

    public BaseView setError(final FreightError error) {
        this.error = error;
        return this;
    }

    public Long getCurrentTime() {
        return currentTime;
    }

    public BaseView setCurrentTime(final long currentTime) {
        this.currentTime = currentTime;
        return this;
    }
}
