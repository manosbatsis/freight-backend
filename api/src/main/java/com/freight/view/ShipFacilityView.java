package com.freight.view;

import com.freight.model.ShipFacility;


/**
 * Created by toshikijahja on 3/26/19.
 */
public class ShipFacilityView {

    private final ShipFacility shipFacility;

    public ShipFacilityView(final ShipFacility shipFacility) {
        this.shipFacility = shipFacility;
    }

    public String getDescription() {
        return shipFacility.getDescription();
    }

    public String getType() {
        return shipFacility.getFacility().getType();
    }

    public String getDisplayName() {
        return shipFacility.getFacility().getDisplayName();
    }
}
