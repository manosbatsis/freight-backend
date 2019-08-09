package com.freight.view;

import com.freight.model.Location;

import java.math.BigDecimal;


/**
 * Created by toshikijahja on 3/26/19.
 */
public class LocationView {

    private final Location location;

    public LocationView(final Location location) {
        this.location = location;
    }

    public int getId() {
        return location.getId();
    }

    public String getExternalId() {
        return location.getExternalId();
    }

    public String getMainName() {
        return location.getMainName();
    }

    public String getSecondaryName() {
        return location.getSecondaryName();
    }

    public BigDecimal getLat() {
        return location.getLat();
    }

    public BigDecimal getLon() {
        return location.getLon();
    }

    public String getRoute() {
        return location.getRoute();
    }

    public String getLocality() {
        return location.getLocality();
    }

    public String getVillage() {
        return location.getVillage();
    }

    public String getSubdistrict() {
        return location.getSubdistrict();
    }

    public String getCity() {
        return location.getCity();
    }

    public String getProvince() {
        return location.getProvince();
    }

    public String getCountry() {
        return location.getCountry();
    }
}
