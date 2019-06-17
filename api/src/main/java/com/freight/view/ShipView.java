package com.freight.view;

import com.freight.model.Ship;


/**
 * Created by toshikijahja on 3/26/19.
 */
public class ShipView extends BaseView {

    private final Ship ship;

    public ShipView(final Ship ship) {
        this.ship = ship;
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
}
