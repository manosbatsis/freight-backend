package com.freight.response;


import com.freight.view.CargoContractView;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class CargoContractResponse extends BaseResponse {

    private final CargoContractView cargoContract;

    public CargoContractResponse(final CargoContractView cargoContract) {
        this.cargoContract = cargoContract;
    }

    public CargoContractView getCargoContract() {
        return cargoContract;
    }
}
