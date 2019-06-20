package com.freight.dao;

import com.freight.model.BulkType;
import com.freight.model.Cargo;
import com.freight.model.CargoType;
import com.freight.model.ContainerType;
import com.freight.model.Shipment;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import java.util.Optional;

import static com.freight.exception.BadRequest.SHIPMENT_NOT_EXIST;
import static com.freight.util.AssertUtil.assertNotNull;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class CargoDao extends BaseDao<Cargo> {

    @AssistedInject
    public CargoDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, Cargo.class);
    }

    public Cargo createCargo(final Shipment shipment,
                             final int userId,
                             final CargoType cargoType,
                             final int quantity,
                             final Optional<Integer> weightOptional,
                             final Cargo.WeightUnit weightUnit,
                             final Optional<Integer> lengthOptional,
                             final Optional<Integer> widthOptional,
                             final Optional<Integer> heightOptional,
                             final Cargo.DimensionUnit dimensionUnit,
                             final Optional<ContainerType> containerTypeOptional,
                             final Optional<BulkType> bulkTypeOptional) {
        assertNotNull(shipment, SHIPMENT_NOT_EXIST);
        getSessionProvider().startTransaction();
        final Cargo cargo = new Cargo.Builder()
                .shipment(shipment)
                .ship(shipment.getShip())
                .userId(userId)
                .cargoType(cargoType)
                .quantity(quantity)
                .weight(weightOptional.map(weight -> weight).orElse(null))
                .weightUnit(weightUnit)
                .length(lengthOptional.map(length -> length).orElse(null))
                .width(widthOptional.map(width -> width).orElse(null))
                .height(heightOptional.map(height -> height).orElse(null))
                .dimensionUnit(dimensionUnit)
                .containerType(containerTypeOptional.map(containerType -> containerType).orElse(null))
                .bulkType(bulkTypeOptional.map(bulkType -> bulkType).orElse(null))
                .build();
        getSessionProvider().getSession().persist(cargo);
        getSessionProvider().commitTransaction();
        return cargo;
    }
}
