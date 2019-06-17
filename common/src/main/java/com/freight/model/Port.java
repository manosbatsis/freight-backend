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
import java.math.BigDecimal;
import java.time.Instant;

/**
 * Created by toshikijahja on 11/7/18.
 */
@Entity
public class Port {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private Integer code;

    @Column
    private String name;

    @Column
    private BigDecimal lat;

    @Column
    private BigDecimal lon;

    @Column
    private String city;

    @Column
    private String province;

    @Column
    private String postalCode;

    @Column
    private String country;

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

    public Port() {}

    private Port(final Builder builder) {
        this.id = builder.id;
        this.code = builder.code;
        this.name = builder.name;
        this.lat = builder.lat;
        this.lon = builder.lon;
        this.city = builder.city;
        this.province = builder.province;
        this.postalCode = builder.postalCode;
        this.country = builder.country;
        this.status = builder.status;
    }

    public int getId() {
        return id;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public Status getStatus() {
        return status;
    }

    public Instant getCreated() {
        return created;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public static class Builder {
        private int id;
        private Integer code;
        private String name;
        private BigDecimal lat;
        private BigDecimal lon;
        private String city;
        private String province;
        private String postalCode;
        private String country;
        private Status status = Status.ACTIVE;

        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        public Builder code(final Integer code) {
            this.code = code;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder lat(final BigDecimal lat) {
            this.lat = lat;
            return this;
        }

        public Builder lon(final BigDecimal lon) {
            this.lon = lon;
            return this;
        }

        public Builder city(final String city) {
            this.city = city;
            return this;
        }

        public Builder province(final String province) {
            this.province = province;
            return this;
        }

        public Builder postalCode(final String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder country(final String country) {
            this.country = country;
            return this;
        }

        public Builder status(final Status status) {
            this.status = status;
            return this;
        }

        public Port build() {
            return new Port(this);
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(code)
                .append(name)
                .append(lat)
                .append(lon)
                .append(city)
                .append(province)
                .append(postalCode)
                .append(country)
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

        final Port that = (Port) o;
        return new EqualsBuilder()
                .append(id, that.id)
                .append(code, that.code)
                .append(name, that.name)
                .append(lat, that.lat)
                .append(lon, that.lon)
                .append(city, that.city)
                .append(province, that.province)
                .append(postalCode, that.postalCode)
                .append(country, that.country)
                .append(status, that.status)
                .append(created, that.created)
                .append(lastModified, that.lastModified)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("id", id)
                .append("code", code)
                .append("name", name)
                .append("lat", lat)
                .append("lon", lon)
                .append("city", city)
                .append("province", province)
                .append("postalCode", postalCode)
                .append("country", country)
                .append("status", status)
                .append("created", created)
                .append("lastModified", lastModified)
                .toString();
    }
}
