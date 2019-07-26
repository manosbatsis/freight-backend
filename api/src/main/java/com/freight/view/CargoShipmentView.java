package com.freight.view;

import com.freight.model.Cargo;
import com.freight.model.Shipment;


/**
 * Created by toshikijahja on 3/26/19.
 */
public class CargoShipmentView {

    private final Cargo cargo;
    private final Shipment shipment;

    public CargoShipmentView(final Cargo cargo,
                             final Shipment shipment) {
        this.cargo = cargo;
        this.shipment = shipment;
    }

    public CargoView getCargo() {
        return new CargoView(cargo);
    }

    public ShipmentView getShipment() {
        return new ShipmentView(shipment);
    }
}
