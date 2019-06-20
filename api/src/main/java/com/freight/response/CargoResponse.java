package com.freight.response;


import com.freight.view.CargoView;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class CargoResponse extends BaseResponse {

    private final CargoView cargo;

    public CargoResponse(final CargoView cargo) {
        this.cargo = cargo;
    }

    public CargoView getCargo() {
        return cargo;
    }
}
