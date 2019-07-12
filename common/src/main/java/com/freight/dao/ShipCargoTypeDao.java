package com.freight.dao;

import com.freight.model.CargoType;
import com.freight.model.Ship;
import com.freight.model.ShipCargoType;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.freight.exception.BadRequest.SHIP_NOT_EXIST;
import static com.freight.util.AssertUtil.assertNotNull;
import static java.util.stream.Collectors.toList;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class ShipCargoTypeDao extends BaseDao<ShipCargoType> {

    @AssistedInject
    public ShipCargoTypeDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, ShipCargoType.class);
    }

    public List<ShipCargoType> createShipCargoTypes(final Ship ship,
                                                    final List<CargoType> cargoTypes) {
        assertNotNull(ship, SHIP_NOT_EXIST);
        getSessionProvider().startTransaction();
        final List<ShipCargoType> shipCargoTypes = cargoTypes.stream().map(cargoType -> {
            final ShipCargoType shipCargoType = new ShipCargoType.Builder()
                    .cargoType(cargoType)
                    .ship(ship)
                    .build();
            getSessionProvider().getSession().persist(shipCargoType);
            return shipCargoType;
        }).collect(toList());
        getSessionProvider().commitTransaction();
        return shipCargoTypes;
    }

    public List<ShipCargoType> getByShipIdAndCargoTypeId(final int shipId,
                                                         final int cargoTypeId) {
        final Map<String, Object> inputParams = new HashMap<>();
        inputParams.put("shipId", shipId);
        inputParams.put("cargoTypeId", cargoTypeId);
        return getByFields("ship.id = :shipId AND cargoType.id = :cargoTypeId", inputParams);
    }
}
