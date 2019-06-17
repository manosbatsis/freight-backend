package com.freight.request_body;

import com.freight.model.Company;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by toshikijahja on 3/26/19.
 */
@ApiModel
public class CompanyRequestBody implements Serializable {

    @ApiModelProperty(value = "Name of the company")
    private String name;

    @ApiModelProperty(value = "Type of the company")
    private Company.Type type;

    public String getName() {
        return name;
    }

    public Company.Type getType() {
        return type;
    }
}
