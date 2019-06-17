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
import java.math.BigDecimal;
import java.time.Instant;

import static com.freight.model.Cargo.Status.INQUIRY;

/**
 * Created by toshikijahja on 3/26/19.
 */
@Entity
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipId")
    private Ship ship;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipmentId")
    private Shipment shipment;

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
    private Integer weight;

    @Column
    @Enumerated(EnumType.STRING)
    private WeightUnit weightUnit;

    @Column
    private Integer length;

    @Column
    private Integer width;

    @Column
    private Integer height;

    @Column
    private DimensionUnit dimensionUnit;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "containerTypeId")
    private ContainerType containerType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bulkTypeId")
    private BulkType bulkType;

    @Column
    private BigDecimal price;

    @Column
    private String currency;

    @Column
    @CreationTimestamp
    private Instant created;

    @Column
    @UpdateTimestamp
    private Instant lastModified;

    public enum Status {
        INQUIRY,
        RESERVED,
        LOADED,
        DELIVERED,
        DELAYED,
        CANCELED
    }

    public enum WeightUnit {
        KG,
        TON,
        LB,
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
        this.ship = builder.ship;
        this.shipment = builder.shipment;
        this.userId = builder.userId;
        this.status = builder.status;
        this.cargoType = builder.cargoType;
        this.quantity = builder.quantity;
        this.weight = builder.weight;
        this.weightUnit = builder.weightUnit;
        this.length = builder.length;
        this.width = builder.width;
        this.height = builder.height;
        this.dimensionUnit = builder.dimensionUnit;
        this.containerType = builder.containerType;
        this.bulkType = builder.bulkType;
        this.price = builder.price;
        this.currency = builder.currency;
    }

    public int getId() {
        return this.id;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public int getUserId() {
        return userId;
    }

    public CargoType getCargoType() {
        return cargoType;
    }

    public int getQuantity() {
        return quantity;
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

    public BigDecimal getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public Instant getCreated() {
        return this.created;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public static class Builder {
        private int id;
        private Ship ship;
        private Shipment shipment;
        private int userId;
        private Status status = INQUIRY;
        private CargoType cargoType;
        private int quantity;
        private Integer weight;
        private WeightUnit weightUnit = WeightUnit.NOT_USED;
        private Integer length;
        private Integer width;
        private Integer height;
        private DimensionUnit dimensionUnit = DimensionUnit.NOT_USED;
        private ContainerType containerType;
        private BulkType bulkType;
        private BigDecimal price;
        private String currency;

        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        public Builder ship(final Ship ship) {
            this.ship = ship;
            return this;
        }

        public Builder shipment(final Shipment shipment) {
            this.shipment = shipment;
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

        public Builder price(final BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder currency(final String currency) {
            this.currency = currency;
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
                .append(ship)
                .append(shipment)
                .append(userId)
                .append(status)
                .append(cargoType)
                .append(quantity)
                .append(weight)
                .append(weightUnit)
                .append(length)
                .append(width)
                .append(height)
                .append(dimensionUnit)
                .append(containerType)
                .append(bulkType)
                .append(price)
                .append(currency)
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
                .append(ship, that.ship)
                .append(shipment, that.shipment)
                .append(userId, that.userId)
                .append(status, that.status)
                .append(cargoType, that.cargoType)
                .append(quantity, that.quantity)
                .append(weight, that.weight)
                .append(weightUnit, that.quantity)
                .append(length, that.length)
                .append(width, that.width)
                .append(height, that.height)
                .append(dimensionUnit, that.dimensionUnit)
                .append(containerType, that.containerType)
                .append(bulkType, that.bulkType)
                .append(price, that.price)
                .append(currency, that.currency)
                .append(created, that.created)
                .append(lastModified, that.lastModified)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("id", id)
                .append("ship", ship)
                .append("shipment", shipment)
                .append("userId", userId)
                .append("status", status)
                .append("cargoType", cargoType)
                .append("quantity", quantity)
                .append("weight", weight)
                .append("weightUnit", quantity)
                .append("length", length)
                .append("width", width)
                .append("height", height)
                .append("dimensionUnit", dimensionUnit)
                .append("containerType", containerType)
                .append("bulkType", bulkType)
                .append("price", price)
                .append("currency", currency)
                .append("created", created)
                .append("lastModified", lastModified)
                .toString();
    }
}
