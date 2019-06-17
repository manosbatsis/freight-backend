package com.freight.response;


import com.freight.view.PortView;

import java.util.List;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class PortListResponse extends BaseResponse {

    private final List<PortView> ports;

    public PortListResponse(final List<PortView> ports) {
        this.ports = ports;
    }

    public List<PortView> getPorts() {
        return ports;
    }
}
