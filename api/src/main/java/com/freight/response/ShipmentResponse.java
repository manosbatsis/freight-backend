package com.freight.response;


import com.freight.view.ShipmentView;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class ShipmentResponse extends BaseResponse {

    private final ShipmentView shipment;

    public ShipmentResponse(final ShipmentView shipment) {
        this.shipment = shipment;
    }

    public ShipmentView getShipment() {
        return shipment;
    }
}
