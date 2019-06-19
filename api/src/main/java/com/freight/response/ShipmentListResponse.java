package com.freight.response;


import com.freight.view.ShipmentView;

import java.util.List;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class ShipmentListResponse extends BaseResponse {

    private final List<ShipmentView> shipments;

    public ShipmentListResponse(final List<ShipmentView> shipments) {
        this.shipments = shipments;
    }

    public List<ShipmentView> getShipments() {
        return shipments;
    }
}
