package com.freight.view;

import com.freight.model.Shipment;

import java.time.Instant;


/**
 * Created by toshikijahja on 3/26/19.
 */
public class ShipmentView {

    private final Shipment shipment;

    public ShipmentView(final Shipment shipment) {
        this.shipment = shipment;
    }

    public int getId() {
        return shipment.getId();
    }

    public ShipView getShip() {
        return new ShipView(shipment.getShip());
    }

    public PortView getOriginPort() {
        return new PortView(shipment.getOriginPort());
    }

    public PortView getDestinationPort() {
        return new PortView(shipment.getDestinationPort());
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
