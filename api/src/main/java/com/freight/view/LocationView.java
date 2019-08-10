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
}
