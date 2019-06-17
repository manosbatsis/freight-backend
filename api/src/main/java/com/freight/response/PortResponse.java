package com.freight.response;


import com.freight.view.PortView;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class PortResponse extends BaseResponse {

    private final PortView port;

    public PortResponse(final PortView port) {
        this.port = port;
    }

    public PortView getPort() {
        return port;
    }
}
