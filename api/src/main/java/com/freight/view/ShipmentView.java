package com.freight.view;

import com.freight.model.ShipFacility;
import com.freight.model.Shipment;

import java.time.Instant;
import java.util.List;


/**
 * Created by toshikijahja on 3/26/19.
 */
public class ShipmentView {

    private final Shipment shipment;
    private final List<ShipFacility> shipFacilities;

    public ShipmentView(final Shipment shipment,
                        final List<ShipFacility> shipFacilities) {
        this.shipment = shipment;
        this.shipFacilities = shipFacilities;
    }

    public int getId() {
        return shipment.getId();
    }

    public ShipView getShip() {
        return new ShipView(shipment.getShip(), shipFacilities);
    }

    public LocationView getOrigin() {
        return new LocationView(shipment.getOriginLocation());
    }

    public LocationView getDestination() {
        return new LocationView(shipment.getDestinationLocation());
    }

    public Instant getDeparture() {
        return shipment.getDeparture();
    }

    public Instant getArrival() {
        return shipment.getArrival();
    }

    public Shipment.Status getStatus() {
        return shipment.getStatus();
    }

    public Shipment.ShipStatus getShipStatus() {
        return shipment.getShipStatus();
    }
}
