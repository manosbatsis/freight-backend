package com.freight.view;

import com.freight.model.Ship;
import com.freight.model.ShipFacility;

import java.util.List;

import static java.util.stream.Collectors.toList;


/**
 * Created by toshikijahja on 3/26/19.
 */
public class ShipView {

    private final Ship ship;
    private final List<ShipFacility> shipFacilities;

    public ShipView(final Ship ship,
                    final List<ShipFacility> shipFacilities) {
        this.ship = ship;
        this.shipFacilities = shipFacilities;
    }

    public int getId() {
        return ship.getId();
    }

    public String getName() {
        return ship.getName();
    }

    public CompanyView getCompany() {
        return new CompanyView(ship.getCompany());
    }

    public Integer getYearBuilt() {
        return ship.getYearBuilt();
    }

    public Integer getGrossTonnage() {
        return ship.getGrossTonnage();
    }

    public Ship.Status getStatus() {
        return ship.getStatus();
    }

    public ShipTypeView getType() {
        return new ShipTypeView(ship.getShipType());
    }

    public List<ShipFacilityView> getShipFacilities() {
        return shipFacilities.stream().map(ShipFacilityView::new).collect(toList());
    }
}
