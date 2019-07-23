package com.freight.view;

import com.freight.model.ShipAgent;
import com.freight.model.Type;

import java.math.BigDecimal;


/**
 * Created by toshikijahja on 3/26/19.
 */
public class ShipAgentView {

    private final ShipAgent shipAgent;

    public ShipAgentView(final ShipAgent shipAgent) {
        this.shipAgent = shipAgent;
    }

    public int getId() {
        return shipAgent.getId();
    }

    public Type getAssigner() {
        return shipAgent.getAssigner();
    }

    public BigDecimal getCustomerShare() {
        return shipAgent.getCustomerShare();
    }

    public BigDecimal getTransporterShare() {
        return shipAgent.getTransporterShare();
    }
}
