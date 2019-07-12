package com.freight.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

/**
 * Created by toshikijahja on 3/26/19.
 */
@Entity
public class CargoContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int cargoId;

    @Column
    private int contractId;

    @Column
    private int customerId;

    @Column
    private int transporterId;

    @Column
    @Enumerated(EnumType.STRING)
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
        TRANSPORTER_OFFERED,
        CUSTOMER_ACCEPT,
        CUSTOMER_DECLINE,
        CUSTOMER_NEGOTIATE,
        CANCELED,
        CUSTOMER_EXPIRED,
        TRANSPORTER_EXPIRED,
    }

    public CargoContract() {}

    private CargoContract(final Builder builder) {
        this.id = builder.id;
        this.cargoId = builder.cargoId;
        this.contractId = builder.contractId;
        this.customerId = builder.customerId;
        this.transporterId = builder.transporterId;
        this.status = builder.status;
        this.expiry = builder.expiry;
    }

    public int getId() {
        return this.id;
    }

    public int getCargoId() {
        return cargoId;
    }

    public int getContractId() {
        return contractId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getTransporterId() {
        return transporterId;
    }

    public Status getStatus() {
        return status;
    }

    public Instant getExpiry() {
        return expiry;
    }

    public Instant getCreated() {
        return created;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public static class Builder {
        private int id;
        private int cargoId;
        private int contractId;
        private int customerId;
        private int transporterId;
        private Status status;
        private Instant expiry;

        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        public Builder cargoId(final int cargoId) {
            this.cargoId = cargoId;
            return this;
        }

        public Builder contractId(final int contractId) {
            this.contractId = contractId;
            return this;
        }

        public Builder customerId(final int customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder transporterId(final int transporterId) {
            this.transporterId = transporterId;
            return this;
        }

        public Builder status(final Status status) {
            this.status = status;
            return this;
        }

        public Builder expiry(final Instant expiry) {
            this.expiry = expiry;
            return this;
        }

        public CargoContract build() {
            return new CargoContract(this);
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(cargoId)
                .append(contractId)
                .append(customerId)
                .append(transporterId)
                .append(status)
                .append(expiry)
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

        final CargoContract that = (CargoContract) o;
        return new EqualsBuilder()
                .append(id, that.id)
                .append(cargoId, that.cargoId)
                .append(contractId, that.contractId)
                .append(customerId, that.customerId)
                .append(transporterId, that.transporterId)
                .append(status, that.status)
                .append(expiry, that.expiry)
                .append(created, that.created)
                .append(lastModified, that.lastModified)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("id", id)
                .append("cargoId", cargoId)
                .append("contractId", contractId)
                .append("customerId", customerId)
                .append("transporterId", transporterId)
                .append("status", status)
                .append("expiry", expiry)
                .append("created", created)
                .append("lastModified", lastModified)
                .toString();
    }
}
