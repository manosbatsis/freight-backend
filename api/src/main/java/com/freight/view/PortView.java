package com.freight.view;

import com.freight.model.Port;

import java.math.BigDecimal;


/**
 * Created by toshikijahja on 3/26/19.
 */
public class PortView {

    private final Port port;

    public PortView(final Port port) {
        this.port = port;
    }

    public int getId() {
        return port.getId();
    }

    public Integer getCode() {
        return port.getCode();
    }

    public String getName() {
        return port.getName();
    }

    public BigDecimal getLat() {
        return port.getLat();
    }

    public BigDecimal getLon() {
        return port.getLon();
    }

    public String getCity() {
        return port.getCity();
    }

    public String getProvince() {
        return port.getProvince();
    }

    public String getIsland() {
        return port.getIsland();
    }

    public String getCountry() {
        return port.getCountry();
    }

    public Port.Status getStatus() {
        return port.getStatus();
    }

    public Port.Size getSize() {
        return port.getSize();
    }
}
