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
import java.time.Instant;

/**
 * Created by toshikijahja on 7/29/17.
 */
@Entity
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    private String type;

    @Column
    @CreationTimestamp
    private Instant created;

    @Column
    @UpdateTimestamp
    private Instant lastModified;

    public Facility() {}

    private Facility(final Builder builder) {
        this.id = builder.id;
        this.displayName = builder.displayName;
        this.type = builder.type;

    }

    public int getId() {
        return this.id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getType() {
        return this.type;
    }

    public Instant getCreated() {
        return this.created;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public static class Builder {
        private int id;
        private String displayName;
        private String type;

        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        public Builder displayName(final String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder type(final String type) {
            this.type = type;
            return this;
        }

        public Facility build() {
            return new Facility(this);
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(displayName)
                .append(type)
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

        final Facility that = (Facility) o;
        return new EqualsBuilder()
                .append(id, that.id)
                .append(displayName, that.displayName)
                .append(type, that.type)
                .append(created, that.created)
                .append(lastModified, that.lastModified)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("id", id)
                .append("displayName", displayName)
                .append("type", type)
                .append("created", created)
                .append("lastModified", lastModified)
                .toString();
    }
}
