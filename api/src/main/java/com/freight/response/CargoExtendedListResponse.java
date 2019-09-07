package com.freight.response;


import com.freight.view.CargoExtendedView;

import java.util.List;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class CargoExtendedListResponse extends BaseResponse {

    private final List<CargoExtendedView> cargos;

    public CargoExtendedListResponse(final List<CargoExtendedView> cargos) {
        this.cargos = cargos;
    }

    public List<CargoExtendedView> getCargos() {
        return cargos;
    }
}
