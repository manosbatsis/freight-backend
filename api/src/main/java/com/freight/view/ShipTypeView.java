package com.freight.view;

import com.freight.model.ShipType;


/**
 * Created by toshikijahja on 3/26/19.
 */
public class ShipTypeView {

    private final ShipType shipType;

    public ShipTypeView(final ShipType shipType) {
        this.shipType = shipType;
    }

    public int getId() {
        return shipType.getId();
    }

    public String getDisplayName() {
        return shipType.getDisplayName();
    }

    public String getType() {
        return shipType.getType();
    }
}
