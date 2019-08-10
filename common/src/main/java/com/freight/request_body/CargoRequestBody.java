package com.freight.request_body;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.freight.model.Cargo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by toshikijahja on 3/26/19.
 */
@ApiModel
public class CargoRequestBody implements Serializable {

    @ApiModelProperty(value = "Id of the cargo type")
    private int cargoTypeId;

    @ApiModelProperty(value = "Quantity of the cargo")
    private int quantity;

    @ApiModelProperty(value = "Origin of where the cargo will be shipped from")
    private LocationRequestBody origin;

    @ApiModelProperty(value = "Destination of where the cargo will be shipped to")
    private LocationRequestBody destination;

    @ApiModelProperty(value = "Departure date of the cargo")
    private long departure;

    @ApiModelProperty(value = "Weight of the cargo")
    @JsonProperty
    private Integer weight;

    @ApiModelProperty(value = "Weight unit of the cargo")
    private Cargo.WeightUnit weightUnit = Cargo.WeightUnit.NOT_USED;

    @ApiModelProperty(value = "Volume of the cargo")
    @JsonProperty
    private Integer volume;

    @ApiModelProperty(value = "Volume unit of the cargo")
    private Cargo.VolumeUnit volumeUnit = Cargo.VolumeUnit.NOT_USED;

    @ApiModelProperty(value = "Length dimension of the cargo")
    @JsonProperty
    private Integer length;

    @ApiModelProperty(value = "Width dimension of the cargo")
    @JsonProperty
    private Integer width;

    @ApiModelProperty(value = "Height dimension of the cargo")
    @JsonProperty
    private Integer height;

    @ApiModelProperty(value = "Dimension unit of the cargo")
    private Cargo.DimensionUnit dimensionUnit = Cargo.DimensionUnit.NOT_USED;

    @ApiModelProperty(value = "Id of the container type of the cargo")
    @JsonProperty
    private Integer containerTypeId;

    @ApiModelProperty(value = "Id of the bulk type of the cargo")
    @JsonProperty
    private Integer bulkTypeId;

    public int getCargoTypeId() {
        return cargoTypeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocationRequestBody getOrigin() {
        return origin;
    }

    public LocationRequestBody getDestination() {
        return destination;
    }

    public long getDeparture() {
        return departure;
    }

    public Optional<Integer> getWeightOptional() {
        return Optional.ofNullable(weight);
    }

    public Cargo.WeightUnit getWeightUnit() {
        return weightUnit;
    }

    public Optional<Integer> getVolumeOptional() {
        return Optional.ofNullable(volume);
    }

    public Cargo.VolumeUnit getVolumeUnit() {
        return volumeUnit;
    }

    public Optional<Integer> getLengthOptional() {
        return Optional.ofNullable(length);
    }

    public Optional<Integer> getWidthOptional() {
        return Optional.ofNullable(width);
    }

    public Optional<Integer> getHeightOptional() {
        return Optional.ofNullable(height);
    }

    public Cargo.DimensionUnit getDimensionUnit() {
        return dimensionUnit;
    }

    public Optional<Integer> getContainerTypeIdOptional() {
        return Optional.ofNullable(containerTypeId);
    }

    public Optional<Integer> getBulkTypeIdOptional() {
        return Optional.ofNullable(bulkTypeId);
    }
}
