package com.freight.response;


import com.freight.view.CargoView;

import java.util.List;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class CargoListResponse extends BaseResponse {

    private final List<CargoView> cargos;

    public CargoListResponse(final List<CargoView> cargos) {
        this.cargos = cargos;
    }

    public List<CargoView> getCargos() {
        return cargos;
    }
}
