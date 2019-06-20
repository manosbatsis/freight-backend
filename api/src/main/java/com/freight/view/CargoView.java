package com.freight.view;

import com.freight.model.Cargo;

import java.math.BigDecimal;


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

    public ShipmentView getShipment() {
        return cargo.getShipment() != null ? new ShipmentView(cargo.getShipment()) : null;
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

    public Integer getWeight() {
        return cargo.getWeight();
    }

    public Cargo.WeightUnit getWeightUnit() {
        return cargo.getWeightUnit();
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

    public BigDecimal getPrice() {
        return cargo.getPrice();
    }

    public String getCurrency() {
        return cargo.getCurrency();
    }
}
