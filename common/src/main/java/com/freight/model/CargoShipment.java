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
    @JoinColumn(name = "shipmentId")
    private Shipment shipment;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cargoId")
    private Cargo cargo;

    @Column
    private Status status;

    @Column
    private Instant expiry;

    @Column
    @CreationTimestamp
    private Instant created;

    @Column
    @UpdateTimestamp
    private Instant lastModified;

    public enum Status {
        TRANSPORTER_OFFER,
        CUSTOMER_ACCEPT,
        CUSTOMER_DECLINE,

    }

    public CargoShipment() {}

    private CargoShipment(final Builder builder) {
        this.shipment = builder.shipment;
        this.cargo = builder.cargo;
    }

    public Shipment getShipment() {
        return this.shipment;
    }

    public Cargo getCargo() {
        return this.cargo;
    }

    public Instant getCreated() {
        return this.created;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public static class CargoShipmentPK implements Serializable {
        private Shipment shipment;
        private Cargo cargo;

        public CargoShipmentPK() {}

        public CargoShipmentPK(final Shipment shipment, final Cargo cargo) {
            this.shipment = shipment;
            this.cargo = cargo;
        }
    }

    public static class Builder {
        private Shipment shipment;
        private Cargo cargo;

        public Builder shipment(final Shipment shipment) {
            this.shipment = shipment;
            return this;
        }

        public Builder cargo(final Cargo cargo) {
            this.cargo = cargo;
            return this;
        }

        public CargoShipment build() {
            return new CargoShipment(this);
        }
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(shipment)
                .append(cargo)
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
                .append(shipment, that.shipment)
                .append(cargo, that.cargo)
                .append(created, that.created)
                .append(lastModified, that.lastModified)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("shipment", shipment)
                .append("cargo", cargo)
                .append("created", created)
                .append("lastModified", lastModified)
                .toString();
    }
}
