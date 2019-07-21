package com.freight.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * Created by toshikijahja on 7/29/17.
 */
@Entity
public class ShipAgent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private Type assigner;

    @Column
    private BigDecimal customerShare;

    @Column
    private BigDecimal transporterShare;

    @Column
    @CreationTimestamp
    private Instant created;

    @Column
    @UpdateTimestamp
    private Instant lastModified;

    public ShipAgent() {}

    private ShipAgent(final Builder builder) {
        this.id = builder.id;
        this.assigner = builder.assigner;
        this.customerShare = builder.customerShare;
        this.transporterShare = builder.transporterShare;
    }

    public int getId() {
        return this.id;
    }

    public Type getAssigner() {
        return this.assigner;
    }

    public BigDecimal getCustomerShare() {
        return this.customerShare;
    }

    public BigDecimal getTransporterShare() {
        return this.transporterShare;
    }

    public Instant getCreated() {
        return this.created;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public static class Builder {
        private int id;
        private Type assigner;
        private BigDecimal customerShare;
        private BigDecimal transporterShare;

        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        public Builder assigner(final Type assigner) {
            this.assigner = assigner;
            return this;
        }

        public Builder customerShare(final BigDecimal customerShare) {
            this.customerShare = customerShare;
            return this;
        }

        public Builder transporterShare(final BigDecimal transporterShare) {
            this.transporterShare = transporterShare;
            return this;
        }

        public ShipAgent build() {
            return new ShipAgent(this);
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(assigner)
                .append(customerShare)
                .append(transporterShare)
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

        final ShipAgent that = (ShipAgent) o;
        return new EqualsBuilder()
                .append(id, that.id)
                .append(assigner, that.assigner)
                .append(customerShare, that.customerShare)
                .append(transporterShare, that.transporterShare)
                .append(created, that.created)
                .append(lastModified, that.lastModified)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("id", id)
                .append("assigner", assigner)
                .append("customerShare", customerShare)
                .append("transporterShare", transporterShare)
                .append("created", created)
                .append("lastModified", lastModified)
                .toString();
    }
}
