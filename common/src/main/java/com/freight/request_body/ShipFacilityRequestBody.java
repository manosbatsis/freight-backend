package com.freight.request_body;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Optional;

/**
 * Created by toshikijahja on 3/26/19.
 */
@ApiModel
public class ShipFacilityRequestBody implements Serializable {

    @ApiModelProperty(value = "Id of the facility")
    private int facilityId;

    @ApiModelProperty(value = "Description of the facility")
    @JsonProperty
    private String description;

    public int getFacilityId() {
        return facilityId;
    }

    public Optional<String> getDescriptionOptional() {
        return Optional.ofNullable(description);
    }
}
