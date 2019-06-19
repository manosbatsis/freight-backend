package com.freight.view;

import com.freight.model.CargoType;


/**
 * Created by toshikijahja on 3/26/19.
 */
public class CargoTypeView {

    private final CargoType cargoType;

    public CargoTypeView(final CargoType cargoType) {
        this.cargoType = cargoType;
    }

    public int getId() {
        return cargoType.getId();
    }

    public String getDisplayName() {
        return cargoType.getDisplayName();
    }

    public String getType() {
        return cargoType.getType();
    }
}
