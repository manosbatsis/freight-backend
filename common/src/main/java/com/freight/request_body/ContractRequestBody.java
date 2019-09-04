package com.freight.request_body;

import com.freight.model.CargoContract;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by toshikijahja on 3/26/19.
 */
@ApiModel
public class ContractRequestBody implements Serializable {

    @ApiModelProperty(value = "Id of the contract")
    private int contractId;

    @ApiModelProperty(value = "Status of the contract")
    private CargoContract.Status status;

    public int getContractId() {
        return contractId;
    }

    public CargoContract.Status getStatus() {
        return status;
    }
}
