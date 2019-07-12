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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;

import static com.freight.model.Shipment.ShipStatus.DOCKING_ORIGIN;

/**
 * Created by toshikijahja on 11/7/18.
 */
@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipId")
    private Ship ship;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "originPortId")
    private Port originPort;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destinationPortId")
    private Port destinationPort;

    @Column
    private Instant departure;

    @Column
    private Instant arrival;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    @Enumerated(EnumType.STRING)
    private ShipStatus shipStatus;

    @Column
    @CreationTimestamp
    private Instant created;

    @Column
    @UpdateTimestamp
    private Instant lastModified;

    public enum Status {
        OPEN,
        CLOSED,
        COMPLETED,
        CANCELED
    }

    public enum ShipStatus {
        DOCKING_ORIGIN,
        LOADING,
        AT_SEA,
        DISCHARGE,
        DOCKING_DESTINATION
    }

    public Shipment() {}

    private Shipment(final Builder builder) {
        this.id = builder.id;
        this.ship = builder.ship;
        this.originPort = builder.originPort;
        this.destinationPort = builder.destinationPort;
        this.departure = builder.departure;
        this.arrival = builder.arrival;
        this.status = builder.status;
        this.shipStatus = builder.shipStatus;
    }

    public int getId() {
        return id;
    }

    public Ship getShip() {
        return ship;
    }

    public Port getOriginPort() {
        return originPort;
    }

    public Port getDestinationPort() {
        return destinationPort;
    }

    public Instant getDeparture() {
        return departure;
    }

    public Instant getArrival() {
        return arrival;
    }

    public Status getStatus() {
        return status;
    }

    public ShipStatus getShipStatus() {
        return shipStatus;
    }

    public Instant getCreated() {
        return created;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public static class Builder {
        private int id;
        private Ship ship;
        private Port originPort;
        private Port destinationPort;
        private Instant departure;
        private Instant arrival;
        private Status status = Status.OPEN;
        private ShipStatus shipStatus = DOCKING_ORIGIN;

        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        public Builder ship(final Ship ship) {
            this.ship = ship;
            return this;
        }

        public Builder originPort(final Port originPort) {
            this.originPort = originPort;
            return this;
        }

        public Builder destinationPort(final Port destinationPort) {
            this.destinationPort = destinationPort;
            return this;
        }

        public Builder departure(final Instant departure) {
            this.departure = departure;
            return this;
        }

        public Builder arrival(final Instant arrival) {
            this.arrival = arrival;
            return this;
        }

        public Builder status(final Status status) {
            this.status = status;
            return this;
        }

        public Builder shipStatus(final ShipStatus shipStatus) {
            this.shipStatus = shipStatus;
            return this;
        }

        public Shipment build() {
            return new Shipment(this);
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(ship)
                .append(originPort)
                .append(destinationPort)
                .append(departure)
                .append(arrival)
                .append(status)
                .append(shipStatus)
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

        final Shipment that = (Shipment) o;
        return new EqualsBuilder()
                .append(id, that.id)
                .append(ship, that.ship)
                .append(originPort, that.originPort)
                .append(destinationPort, that.destinationPort)
                .append(departure, that.departure)
                .append(arrival, that.arrival)
                .append(status, that.status)
                .append(shipStatus, that.shipStatus)
                .append(created, that.created)
                .append(lastModified, that.lastModified)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("id", id)
                .append("ship", ship)
                .append("originPort", originPort)
                .append("destinationPort", destinationPort)
                .append("departure", departure)
                .append("arrival", arrival)
                .append("status", status)
                .append("shipStatus", shipStatus)
                .append("created", created)
                .append("lastModified", lastModified)
                .toString();
    }
}
