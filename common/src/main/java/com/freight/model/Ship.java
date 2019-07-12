package com.freight.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Range;

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

import static com.freight.model.Ship.Status.ACTIVE;

/**
 * Created by toshikijahja on 3/26/19.
 */
@Entity
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "companyId")
    private Company company;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipTypeId")
    private ShipType shipType;

    @Column
    @Range(min = 1000, max = 2100)
    private Integer yearBuilt;

    @Column
    private Integer grossTonnage;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    @CreationTimestamp
    private Instant created;

    @Column
    @UpdateTimestamp
    private Instant lastModified;

    public enum Status {
        ACTIVE,
        INACTIVE
    }

    public Ship() {}

    private Ship(final Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.company = builder.company;
        this.shipType = builder.shipType;
        this.yearBuilt = builder.yearBuilt;
        this.grossTonnage = builder.grossTonnage;
        this.status = builder.status;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Company getCompany() {
        return this.company;
    }

    public ShipType getShipType() {
        return this.shipType;
    }

    public Integer getYearBuilt() {
        return this.yearBuilt;
    }

    public Integer getGrossTonnage() {
        return this.grossTonnage;
    }

    public Status getStatus() {
        return this.status;
    }

    public Instant getCreated() {
        return this.created;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public static class Builder {
        private int id;
        private String name;
        private Company company;
        private ShipType shipType;
        private Integer yearBuilt;
        private Integer grossTonnage;
        private Status status = ACTIVE;

        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder company(final Company company) {
            this.company = company;
            return this;
        }

        public Builder shipType(final ShipType shipType) {
            this.shipType = shipType;
            return this;
        }

        public Builder yearBuilt(final Integer yearBuilt) {
            this.yearBuilt = yearBuilt;
            return this;
        }

        public Builder grossTonnage(final Integer grossTonnage) {
            this.grossTonnage = grossTonnage;
            return this;
        }

        public Builder status(final Status status) {
            this.status = status;
            return this;
        }

        public Ship build() {
            return new Ship(this);
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(name)
                .append(company)
                .append(shipType)
                .append(yearBuilt)
                .append(grossTonnage)
                .append(status)
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

        final Ship that = (Ship) o;
        return new EqualsBuilder()
                .append(id, that.id)
                .append(name, that.name)
                .append(company, that.company)
                .append(shipType, that.shipType)
                .append(yearBuilt, that.yearBuilt)
                .append(grossTonnage, that.grossTonnage)
                .append(status, that.status)
                .append(created, that.created)
                .append(lastModified, that.lastModified)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("id", id)
                .append("name", name)
                .append("company", company)
                .append("shipType", shipType)
                .append("yearBuilt", yearBuilt)
                .append("grossTonnage", grossTonnage)
                .append("status", status)
                .append("created", created)
                .append("lastModified", lastModified)
                .toString();
    }
}
