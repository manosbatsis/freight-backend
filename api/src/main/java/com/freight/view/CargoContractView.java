package com.freight.view;

import com.freight.model.CargoContract;

import java.time.Instant;


/**
 * Created by toshikijahja on 3/26/19.
 */
public class CargoContractView {

    private final CargoContract cargoContract;

    public CargoContractView(final CargoContract cargoContract) {
        this.cargoContract = cargoContract;
    }

    public int getId() {
        return cargoContract.getId();
    }


    public int getCargoId() {
        return cargoContract.getCargoId();
    }

    public int getContractId() {
        return cargoContract.getContractId();
    }

    public int getCustomerId() {
        return cargoContract.getCustomerId();
    }

    public int getTransporterId() {
        return cargoContract.getTransporterId();
    }

    public CargoContract.Status getStatus() {
        return cargoContract.getStatus();
    }

    public Instant getExpiry() {
        return cargoContract.getExpiry();
    }
}
