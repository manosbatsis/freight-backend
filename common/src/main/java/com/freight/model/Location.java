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
 * Created by toshikijahja on 11/7/18.
 */
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String externalId;

    @Column
    private String mainName;

    @Column
    private String secondaryName;

    @Column
    private BigDecimal lat;

    @Column
    private BigDecimal lon;

    @Column
    private String route;

    @Column
    private String locality;

    /**
     * kelurahan / desa
     */
    @Column
    private String village;

    /**
     * kecamatan
     */
    @Column
    private String subdistrict;

    /**
     * kota / kabupaten
     */
    @Column
    private String city;

    @Column
    private String province;

    @Column
    private String country;

    @Column
    @CreationTimestamp
    private Instant created;

    @Column
    @UpdateTimestamp
    private Instant lastModified;

    public Location() {}

    private Location(final Builder builder) {
        this.id = builder.id;
        this.externalId = builder.externalId;
        this.mainName = builder.mainName;
        this.secondaryName = builder.secondaryName;
        this.lat = builder.lat;
        this.lon = builder.lon;
        this.route = builder.route;
        this.locality = builder.locality;
        this.village = builder.village;
        this.subdistrict = builder.subdistrict;
        this.city = builder.city;
        this.province = builder.province;
        this.country = builder.country;
    }

    public int getId() {
        return id;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getMainName() {
        return mainName;
    }

    public String getSecondaryName() {
        return secondaryName;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public String getRoute() {
        return route;
    }

    public String getLocality() {
        return locality;
    }

    public String getVillage() {
        return village;
    }

    public String getSubdistrict() {
        return subdistrict;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getCountry() {
        return country;
    }

    public Instant getCreated() {
        return created;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public static class Builder {
        private int id;
        private String externalId;
        private String mainName;
        private String secondaryName;
        private BigDecimal lat;
        private BigDecimal lon;
        private String route;
        private String locality;
        private String village;
        private String subdistrict;
        private String city;
        private String province;
        private String country;

        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        public Builder externalId(final String externalId) {
            this.externalId = externalId;
            return this;
        }

        public Builder mainName(final String mainName) {
            this.mainName = mainName;
            return this;
        }

        public Builder secondaryName(final String secondaryName) {
            this.secondaryName = secondaryName;
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

        public Builder route(final String route) {
            this.route = route;
            return this;
        }

        public Builder locality(final String locality) {
            this.locality = locality;
            return this;
        }

        public Builder village(final String village) {
            this.village = village;
            return this;
        }

        public Builder subdistrict(final String subdistrict) {
            this.subdistrict = subdistrict;
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

        public Builder country(final String country) {
            this.country = country;
            return this;
        }

        public Location build() {
            return new Location(this);
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(externalId)
                .append(mainName)
                .append(secondaryName)
                .append(lat)
                .append(lon)
                .append(route)
                .append(locality)
                .append(village)
                .append(subdistrict)
                .append(city)
                .append(province)
                .append(country)
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

        final Location that = (Location) o;
        return new EqualsBuilder()
                .append(id, that.id)
                .append(id, that.id)
                .append(externalId, that.externalId)
                .append(mainName, that.mainName)
                .append(secondaryName, that.secondaryName)
                .append(lat, that.lat)
                .append(lon, that.lon)
                .append(route, that.route)
                .append(locality, that.locality)
                .append(village, that.village)
                .append(subdistrict, that.subdistrict)
                .append(city, that.city)
                .append(province, that.province)
                .append(country, that.country)
                .append(created, that.created)
                .append(lastModified, that.lastModified)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("id", id)
                .append("id", id)
                .append("externalId", externalId)
                .append("mainName", mainName)
                .append("secondaryName", secondaryName)
                .append("lat", lat)
                .append("lon", lon)
                .append("route", route)
                .append("locality", locality)
                .append("village", village)
                .append("subdistrict", subdistrict)
                .append("city", city)
                .append("province", province)
                .append("country", country)
                .append("created", created)
                .append("lastModified", lastModified)
                .toString();
    }
}
