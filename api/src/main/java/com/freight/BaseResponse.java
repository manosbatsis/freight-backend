package com.freight;

import com.freight.exception.FreightError;

import java.time.Instant;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class BaseResponse {
    private FreightError error;
    private long currentTime;

    public BaseResponse() {
        currentTime = Instant.now().toEpochMilli();
    }

    public FreightError getError() {
        return error;
    }

    public BaseResponse setError(final FreightError error) {
        this.error = error;
        return this;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public BaseResponse setCurrentTime(final long currentTime) {
        this.currentTime = currentTime;
        return this;
    }

}
