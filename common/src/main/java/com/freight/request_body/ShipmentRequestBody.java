package com.freight.request_body;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by toshikijahja on 3/26/19.
 */
@ApiModel
public class ShipmentRequestBody implements Serializable {

    @ApiModelProperty(value = "Id of the ship for the shipment")
    private int shipId;

    @ApiModelProperty(value = "Origin port id")
    private int originPortId;

    @ApiModelProperty(value = "Destination port id")
    private int destinationPortId;

    @ApiModelProperty(value = "Departure date in epoch seconds")
    private long departure;

    @ApiModelProperty(value = "Arrival date in epoch seconds")
    private long arrival;

    public int getShipId() {
        return shipId;
    }

    public int getOriginPortId() {
        return originPortId;
    }

    public int getDestinationPortId() {
        return destinationPortId;
    }

    public long getDeparture() {
        return departure;
    }

    public long getArrival() {
        return arrival;
    }
}
