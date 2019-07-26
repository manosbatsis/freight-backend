package com.freight.model;

import com.freight.exception.FreightException;
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

import static com.freight.exception.BadRequest.CARGO_STATUS_NOT_EXIST;
import static com.freight.model.Cargo.Status.INQUIRY;

/**
 * Created by toshikijahja on 3/26/19.
 */
@Entity
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private Integer contractId;

    @Column
    private Integer shipmentId;

    @Column
    private int userId;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cargoTypeId")
    private CargoType cargoType;

    @Column
    private int quantity;

    @Column
    private Instant departure;

    @Column
    private Integer weight;

    @Column
    @Enumerated(EnumType.STRING)
    private WeightUnit weightUnit;

    @Column
    private Integer volume;

    @Column
    @Enumerated(EnumType.STRING)
    private VolumeUnit volumeUnit;

    @Column
    private Integer length;

    @Column
    private Integer width;

    @Column
    private Integer height;

    @Column
    @Enumerated(EnumType.STRING)
    private DimensionUnit dimensionUnit;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "containerTypeId")
    private ContainerType containerType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bulkTypeId")
    private BulkType bulkType;

    @Column
    @CreationTimestamp
    private Instant expiry;

    @Column
    @CreationTimestamp
    private Instant created;

    @Column
    @UpdateTimestamp
    private Instant lastModified;

    public enum Status {
        INQUIRY,
        RESERVED,
        DELIVERED,
        CANCELED,
        EXPIRED;

        public static Status getStatus(final String statusInString) {
            for (final Status status : Status.values()) {
                if (status.name().equalsIgnoreCase(statusInString)) {
                    return status;
                }
            }
            throw new FreightException(CARGO_STATUS_NOT_EXIST);
        }
    }

    public enum WeightUnit {
        KG,
        TON,
        LB,
        NOT_USED
    }

    public enum VolumeUnit {
        M3,
        NOT_USED
    }

    public enum DimensionUnit {
        CM,
        M,
        IN,
        FT,
        NOT_USED
    }

    public Cargo() {}

    private Cargo(final Builder builder) {
        this.id = builder.id;
        this.contractId = builder.contractId;
        this.shipmentId = builder.shipmentId;
        this.userId = builder.userId;
        this.status = builder.status;
        this.cargoType = builder.cargoType;
        this.quantity = builder.quantity;
        this.departure = builder.departure;
        this.volume = builder.volume;
        this.volumeUnit = builder.volumeUnit;
        this.weight = builder.weight;
        this.weightUnit = builder.weightUnit;
        this.length = builder.length;
        this.width = builder.width;
        this.height = builder.height;
        this.dimensionUnit = builder.dimensionUnit;
        this.containerType = builder.containerType;
        this.bulkType = builder.bulkType;
        this.expiry = builder.expiry;
    }

    public int getId() {
        return this.id;
    }

    public Integer getContractId() {
        return contractId;
    }

    public Integer getShipmentId() {
        return shipmentId;
    }

    public int getUserId() {
        return userId;
    }

    public Status getStatus() {
        return status;
    }

    public CargoType getCargoType() {
        return cargoType;
    }

    public int getQuantity() {
        return quantity;
    }

    public Instant getDeparture() {
        return departure;
    }

    public Integer getVolume() {
        return volume;
    }

    public VolumeUnit getVolumeUnit() {
        return volumeUnit;
    }

    public Integer getWeight() {
        return weight;
    }

    public WeightUnit getWeightUnit() {
        return weightUnit;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public DimensionUnit getDimensionUnit() {
        return dimensionUnit;
    }

    public ContainerType getContainerType() {
        return containerType;
    }

    public BulkType getBulkType() {
        return bulkType;
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
        private Integer contractId;
        private Integer shipmentId;
        private int userId;
        private Status status = INQUIRY;
        private CargoType cargoType;
        private int quantity;
        private Instant departure;
        private Integer volume;
        private VolumeUnit volumeUnit = VolumeUnit.NOT_USED;
        private Integer weight;
        private WeightUnit weightUnit = WeightUnit.NOT_USED;
        private Integer length;
        private Integer width;
        private Integer height;
        private DimensionUnit dimensionUnit = DimensionUnit.NOT_USED;
        private ContainerType containerType;
        private BulkType bulkType;
        private Instant expiry;

        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        public Builder contractId(final Integer contractId) {
            this.contractId = contractId;
            return this;
        }

        public Builder shipmentId(final Integer shipmentId) {
            this.shipmentId = shipmentId;
            return this;
        }

        public Builder userId(final int userId) {
            this.userId = userId;
            return this;
        }

        public Builder status(final Status status) {
            this.status = status;
            return this;
        }

        public Builder cargoType(final CargoType cargoType) {
            this.cargoType = cargoType;
            return this;
        }

        public Builder quantity(final int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder departure(final Instant departure) {
            this.departure = departure;
            return this;
        }

        public Builder volume(final Integer volume) {
            this.volume = volume;
            return this;
        }

        public Builder volumeUnit(final VolumeUnit volumeUnit) {
            this.volumeUnit = volumeUnit;
            return this;
        }

        public Builder weight(final Integer weight) {
            this.weight = weight;
            return this;
        }

        public Builder weightUnit(final WeightUnit weightUnit) {
            this.weightUnit = weightUnit;
            return this;
        }

        public Builder length(final Integer length) {
            this.length = length;
            return this;
        }

        public Builder width(final Integer width) {
            this.width = width;
            return this;
        }

        public Builder height(final Integer height) {
            this.height = height;
            return this;
        }

        public Builder dimensionUnit(final DimensionUnit dimensionUnit) {
            this.dimensionUnit = dimensionUnit;
            return this;
        }

        public Builder containerType(final ContainerType containerType) {
            this.containerType = containerType;
            return this;
        }

        public Builder bulkType(final BulkType bulkType) {
            this.bulkType = bulkType;
            return this;
        }

        public Builder expiry(final Instant expiry) {
            this.expiry = expiry;
            return this;
        }

        public Cargo build() {
            return new Cargo(this);
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(contractId)
                .append(shipmentId)
                .append(userId)
                .append(status)
                .append(cargoType)
                .append(quantity)
                .append(departure)
                .append(volume)
                .append(volumeUnit)
                .append(weight)
                .append(weightUnit)
                .append(length)
                .append(width)
                .append(height)
                .append(dimensionUnit)
                .append(containerType)
                .append(bulkType)
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

        final Cargo that = (Cargo) o;
        return new EqualsBuilder()
                .append(id, that.id)
                .append(contractId, that.contractId)
                .append(shipmentId, that.shipmentId)
                .append(userId, that.userId)
                .append(status, that.status)
                .append(cargoType, that.cargoType)
                .append(quantity, that.quantity)
                .append(departure, that.departure)
                .append(volume, that.volume)
                .append(volumeUnit, that.volumeUnit)
                .append(weight, that.weight)
                .append(weightUnit, that.weightUnit)
                .append(length, that.length)
                .append(width, that.width)
                .append(height, that.height)
                .append(dimensionUnit, that.dimensionUnit)
                .append(containerType, that.containerType)
                .append(bulkType, that.bulkType)
                .append(expiry, that.expiry)
                .append(created, that.created)
                .append(lastModified, that.lastModified)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("id", id)
                .append("contractId", contractId)
                .append("shipmentId", shipmentId)
                .append("userId", userId)
                .append("status", status)
                .append("cargoType", cargoType)
                .append("quantity", quantity)
                .append("departure", departure)
                .append("volume", volume)
                .append("volumeUnit", volumeUnit)
                .append("weight", weight)
                .append("weightUnit", weightUnit)
                .append("length", length)
                .append("width", width)
                .append("height", height)
                .append("dimensionUnit", dimensionUnit)
                .append("containerType", containerType)
                .append("bulkType", bulkType)
                .append("expiry", expiry)
                .append("created", created)
                .append("lastModified", lastModified)
                .toString();
    }
}
