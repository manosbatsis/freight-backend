package com.freight.response;


import com.freight.view.CargoShipmentView;

import java.util.List;

/**
 * Created by toshikijahja on 3/26/19.
 */
public class CargoShipmentListResponse extends BaseResponse {

    private final List<CargoShipmentView> cargoShipments;

    public CargoShipmentListResponse(final List<CargoShipmentView> cargoShipments) {
        this.cargoShipments = cargoShipments;
    }

    public List<CargoShipmentView> getCargoShipment() {
        return cargoShipments;
    }
}
