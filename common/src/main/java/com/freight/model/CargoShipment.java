package com.freight.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.Instant;

/**
 * Created by toshikijahja on 7/29/17.
 */
@Entity
@IdClass(CargoShipment.CargoShipmentPK.class)
public class CargoShipment {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cargoId")
    private Cargo cargo;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipmentId")
    private Shipment shipment;

    @Column
    @CreationTimestamp
    private Instant created;

    @Column
    @UpdateTimestamp
    private Instant lastModified;

    public CargoShipment() {}

    private CargoShipment(final Builder builder) {
        this.cargo = builder.cargo;
        this.shipment = builder.shipment;
    }

    public Cargo getCargo() {
        return this.cargo;
    }

    public Shipment getShipment() {
        return this.shipment;
    }

    public Instant getCreated() {
        return this.created;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public static class CargoShipmentPK implements Serializable {
        private Cargo cargo;
        private Shipment shipment;

        public CargoShipmentPK() {}

        public CargoShipmentPK(final Cargo cargo, final Shipment shipment) {
            this.cargo = cargo;
            this.shipment = shipment;
        }
    }

    public static class Builder {
        private Cargo cargo;
        private Shipment shipment;

        public Builder cargo(final Cargo cargo) {
            this.cargo = cargo;
            return this;
        }

        public Builder shipment(final Shipment shipment) {
            this.shipment = shipment;
            return this;
        }

        public CargoShipment build() {
            return new CargoShipment(this);
        }
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(cargo)
                .append(shipment)
                .append(created)
                .append(lastModified)
                .toHashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final CargoShipment that = (CargoShipment) o;
        return new EqualsBuilder()
                .append(cargo, that.cargo)
                .append(shipment, that.shipment)
                .append(created, that.created)
                .append(lastModified, that.lastModified)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("cargo", cargo)
                .append("shipment", shipment)
                .append("created", created)
                .append("lastModified", lastModified)
                .toString();
    }
}
