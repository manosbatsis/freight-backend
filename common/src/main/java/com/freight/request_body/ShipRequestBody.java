package com.freight.request_body;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

/**
 * Created by toshikijahja on 3/26/19.
 */
@ApiModel
public class ShipRequestBody implements Serializable {

    @ApiModelProperty(value = "Name of the ship")
    private String name;

    @ApiModelProperty(value = "Id of the company the ship belongs to")
    private int companyId;

    @ApiModelProperty(value = "Ship's built year")
    @JsonProperty
    private Integer yearBuilt;

    @ApiModelProperty(value = "Capacity of the ship in gross tonnage")
    @JsonProperty
    private Integer grossTonnage;

    @ApiModelProperty(value = "Cargo type that can be shipped")
    private List<String> cargoTypes = emptyList();

    public String getName() {
        return name;
    }

    public int getCompanyId() {
        return companyId;
    }

    public Optional<Integer> getYearBuiltOptional() {
        return Optional.ofNullable(yearBuilt);
    }

    public Optional<Integer> getGrossTonnageOptional() {
        return Optional.ofNullable(grossTonnage);
    }

    public List<String> getCargoTypes() {
        return cargoTypes;
    }
}
