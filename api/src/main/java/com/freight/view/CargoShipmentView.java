package com.freight.view;

import com.freight.model.Cargo;
import com.freight.model.ShipFacility;
import com.freight.model.Shipment;

import java.util.List;


/**
 * Created by toshikijahja on 3/26/19.
 */
public class CargoShipmentView {

    private final Cargo cargo;
    private final Shipment shipment;
    private final List<ShipFacility> shipFacilities;

    public CargoShipmentView(final Cargo cargo,
                             final Shipment shipment,
                             final List<ShipFacility> shipFacilities) {
        this.cargo = cargo;
        this.shipment = shipment;
        this.shipFacilities = shipFacilities;
    }

    public CargoView getCargo() {
        return new CargoView(cargo);
    }

    public ShipmentView getShipment() {
        return new ShipmentView(shipment, shipFacilities);
    }
}
