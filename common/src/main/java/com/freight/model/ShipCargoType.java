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
@IdClass(ShipCargoType.ShipCargoTypePK.class)
public class ShipCargoType {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipId")
    private Ship ship;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cargoTypeId")
    private CargoType cargoType;

    @Column
    @CreationTimestamp
    private Instant created;

    @Column
    @UpdateTimestamp
    private Instant lastModified;

    public ShipCargoType() {}

    private ShipCargoType(final Builder builder) {
        this.ship = builder.ship;
        this.cargoType = builder.cargoType;
    }

    public Ship getShip() {
        return this.ship;
    }

    public CargoType getCargoType() {
        return this.cargoType;
    }

    public Instant getCreated() {
        return this.created;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public static class ShipCargoTypePK implements Serializable {
        private Ship ship;
        private CargoType cargoType;

        public ShipCargoTypePK() {}

        public ShipCargoTypePK(final Ship ship, final CargoType cargoType) {
            this.ship = ship;
            this.cargoType = cargoType;
        }
    }

    public static class Builder {
        private Ship ship;
        private CargoType cargoType;

        public Builder ship(final Ship ship) {
            this.ship = ship;
            return this;
        }

        public Builder cargoType(final CargoType cargoType) {
            this.cargoType = cargoType;
            return this;
        }

        public ShipCargoType build() {
            return new ShipCargoType(this);
        }
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(ship)
                .append(cargoType)
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

        final ShipCargoType that = (ShipCargoType) o;
        return new EqualsBuilder()
                .append(ship, that.ship)
                .append(cargoType, that.cargoType)
                .append(created, that.created)
                .append(lastModified, that.lastModified)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("ship", ship)
                .append("cargoType", cargoType)
                .append("created", created)
                .append("lastModified", lastModified)
                .toString();
    }
}
