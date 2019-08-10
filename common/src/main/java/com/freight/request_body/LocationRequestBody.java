package com.freight.request_body;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by toshikijahja on 8/9/19.
 */
@ApiModel
public class LocationRequestBody implements Serializable {

    @ApiModelProperty(value = "External Id of the location")
    private String externalId;

    @ApiModelProperty(value = "Main name of the location")
    private String mainName;

    @ApiModelProperty(value = "Secondary name of the location")
    private String secondaryName;

    @ApiModelProperty(value = "Latitude of the location")
    private BigDecimal lat;

    @ApiModelProperty(value = "Longitude of the location")
    private BigDecimal lon;

    @ApiModelProperty(value = "Route name of the location")
    private String route;

    @ApiModelProperty(value = "Locality name of the location")
    private String locality;

    @ApiModelProperty(value = "Village name of the location")
    private String village;

    @ApiModelProperty(value = "Subdistrict name of the location")
    private String subdistrict;

    @ApiModelProperty(value = "City name of the location")
    private String city;

    @ApiModelProperty(value = "Province name of the location")
    private String province;

    @ApiModelProperty(value = "Country name of the location")
    private String country;

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
}
