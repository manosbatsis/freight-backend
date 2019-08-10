package com.freight.view;

import com.freight.model.Cargo;

import java.time.Instant;


/**
 * Created by toshikijahja on 3/26/19.
 */
public class CargoView {

    private final Cargo cargo;

    public CargoView(final Cargo cargo) {
        this.cargo = cargo;
    }

    public int getId() {
        return cargo.getId();
    }

    public int getUserId() {
        return cargo.getUserId();
    }

    public Cargo.Status getStatus() {
        return cargo.getStatus();
    }

    public CargoTypeView getCargoType() {
        return new CargoTypeView(cargo.getCargoType());
    }

    public int getQuantity() {
        return cargo.getQuantity();
    }

    public LocationView getOrigin() {
        return new LocationView(cargo.getOriginLocation());
    }

    public LocationView getDestination() {
        return new LocationView(cargo.getDestinationLocation());
    }

    public Instant getDeparture() {
        return cargo.getDeparture();
    }

    public Integer getWeight() {
        return cargo.getWeight();
    }

    public Cargo.WeightUnit getWeightUnit() {
        return cargo.getWeightUnit();
    }

    public Integer getVolume() {
        return cargo.getVolume();
    }

    public Cargo.VolumeUnit getVolumeUnit() {
        return cargo.getVolumeUnit();
    }

    public Integer getLength() {
        return cargo.getLength();
    }

    public Integer getWidth() {
        return cargo.getWidth();
    }

    public Integer getHeight() {
        return cargo.getHeight();
    }

    public Cargo.DimensionUnit getDimensionUnit() {
        return cargo.getDimensionUnit();
    }

    public ContainerTypeView getContainerType() {
        return cargo.getContainerType() != null ? new ContainerTypeView(cargo.getContainerType()) : null;
    }

    public BulkTypeView getBulkType() {
        return cargo.getBulkType() != null ? new BulkTypeView(cargo.getBulkType()) : null;
    }
}
