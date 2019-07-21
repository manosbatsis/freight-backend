package com.freight.model;

import com.freight.exception.FreightException;
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

import static com.freight.exception.BadRequest.PAYOUT_NOT_SUM_UP;
import static java.math.BigDecimal.valueOf;

/**
 * Created by toshikijahja on 7/29/17.
 */
@Entity
public class Payout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private BigDecimal contractSigned;

    @Column
    private BigDecimal dockedOrigin;

    @Column
    private BigDecimal loaded;

    @Column
    private BigDecimal dockedDestination;

    @Column
    private BigDecimal discharged;

    @Column
    @CreationTimestamp
    private Instant created;

    @Column
    @UpdateTimestamp
    private Instant lastModified;

    public Payout() {}

    private Payout(final Builder builder) {
        this.id = builder.id;
        this.contractSigned = builder.contractSigned;
        this.dockedOrigin = builder.dockedOrigin;
        this.loaded = builder.loaded;
        this.dockedDestination = builder.dockedDestination;
        this.discharged = builder.discharged;
    }

    public int getId() {
        return this.id;
    }

    public BigDecimal getContractSigned() {
        return this.contractSigned;
    }

    public BigDecimal getDockedOrigin() {
        return this.dockedOrigin;
    }

    public BigDecimal getLoaded() {
        return this.loaded;
    }

    public BigDecimal getDockedDestination() {
        return this.dockedDestination;
    }

    public BigDecimal getDischarged() {
        return this.discharged;
    }

    public Instant getCreated() {
        return this.created;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    /**
     * throw PAYOUT_NOT_SUM_UP if total payments don't sum up to 1
     */
    public static void assertSumToOne(final Builder builder) {
        final BigDecimal sum = builder.contractSigned
                .add(builder.dockedOrigin)
                .add(builder.loaded)
                .add(builder.dockedDestination)
                .add(builder.discharged);
        if (sum.compareTo(valueOf(1)) != 0) {
            throw new FreightException(PAYOUT_NOT_SUM_UP);
        }
    }

    public static class Builder {
        private int id;
        private BigDecimal contractSigned = valueOf(0);
        private BigDecimal dockedOrigin = valueOf(0);
        private BigDecimal loaded = valueOf(0);
        private BigDecimal dockedDestination = valueOf(0);
        private BigDecimal discharged = valueOf(0);

        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        public Builder contractSigned(final BigDecimal contractSigned) {
            this.contractSigned = contractSigned;
            return this;
        }

        public Builder dockedOrigin(final BigDecimal dockedOrigin) {
            this.dockedOrigin = dockedOrigin;
            return this;
        }

        public Builder loaded(final BigDecimal loaded) {
            this.loaded = loaded;
            return this;
        }

        public Builder dockedDestination(final BigDecimal dockedDestination) {
            this.dockedDestination = dockedDestination;
            return this;
        }

        public Builder discharged(final BigDecimal discharged) {
            this.discharged = discharged;
            return this;
        }

        public Payout build() {
            assertSumToOne(this);
            return new Payout(this);
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(contractSigned)
                .append(dockedOrigin)
                .append(loaded)
                .append(dockedDestination)
                .append(discharged)
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

        final Payout that = (Payout) o;
        return new EqualsBuilder()
                .append(id, that.id)
                .append(contractSigned, that.contractSigned)
                .append(dockedOrigin, that.dockedOrigin)
                .append(loaded, that.loaded)
                .append(dockedDestination, that.dockedDestination)
                .append(discharged, that.discharged)
                .append(created, that.created)
                .append(lastModified, that.lastModified)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("id", id)
                .append("contractSigned", contractSigned)
                .append("dockedOrigin", dockedOrigin)
                .append("loaded", loaded)
                .append("dockedDestination", dockedDestination)
                .append("discharged", discharged)
                .append("created", created)
                .append("lastModified", lastModified)
                .toString();
    }
}
