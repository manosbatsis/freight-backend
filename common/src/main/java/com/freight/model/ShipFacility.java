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
@IdClass(ShipFacility.ShipFacilityPK.class)
public class ShipFacility {

    @Id
    private int shipId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "facilityId")
    private Facility facility;

    @Column
    private String description;

    @Column
    @CreationTimestamp
    private Instant created;

    @Column
    @UpdateTimestamp
    private Instant lastModified;

    public ShipFacility() {}

    private ShipFacility(final Builder builder) {
        this.shipId = builder.shipId;
        this.facility = builder.facility;
    }

    public int getShipId() {
        return this.shipId;
    }

    public Facility getFacility() {
        return this.facility;
    }

    public String getDescription() {
        return this.description;
    }

    public Instant getCreated() {
        return this.created;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public static class ShipFacilityPK implements Serializable {
        private int shipId;
        private Facility facility;

        public ShipFacilityPK() {}

        public ShipFacilityPK(final int shipId, final Facility facility) {
            this.shipId = shipId;
            this.facility = facility;
        }
    }

    public static class Builder {
        private int shipId;
        private String description;
        private Facility facility;

        public Builder shipId(final int shipId) {
            this.shipId = shipId;
            return this;
        }

        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        public Builder facility(final Facility facility) {
            this.facility = facility;
            return this;
        }

        public ShipFacility build() {
            return new ShipFacility(this);
        }
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(shipId)
                .append(facility)
                .append(description)
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

        final ShipFacility that = (ShipFacility) o;
        return new EqualsBuilder()
                .append(shipId, that.shipId)
                .append(facility, that.facility)
                .append(description, that.description)
                .append(created, that.created)
                .append(lastModified, that.lastModified)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("shipId", shipId)
                .append("facility", facility)
                .append("description", description)
                .append("created", created)
                .append("lastModified", lastModified)
                .toString();
    }
}
