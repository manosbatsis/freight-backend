package com.freight.response;


import com.freight.view.ShipView;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class ShipResponse extends BaseResponse {

    private final ShipView ship;

    public ShipResponse(final ShipView ship) {
        this.ship = ship;
    }

    public ShipView getShip() {
        return ship;
    }
}
