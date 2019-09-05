package com.freight.dao;

import com.freight.model.BulkType;
import com.freight.model.Cargo;
import com.freight.model.CargoType;
import com.freight.model.ContainerType;
import com.freight.model.Location;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.hibernate.query.Query;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.freight.dao.BaseDao.Sort.DESC;
import static com.freight.exception.BadRequest.STATUS_NOT_EXIST;
import static com.freight.util.AssertUtil.assertNotNull;
import static io.jsonwebtoken.lang.Collections.isEmpty;
import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class CargoDao extends BaseDao<Cargo> {

    @AssistedInject
    public CargoDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, Cargo.class);
    }

    public Cargo createCargo(final int userId,
                             final CargoType cargoType,
                             final int quantity,
                             final Location originLocation,
                             final Location destinationLocation,
                             final Instant departure,
                             final Optional<Integer> weightOptional,
                             final Cargo.WeightUnit weightUnit,
                             final Optional<Integer> volumeOptional,
                             final Cargo.VolumeUnit volumeUnit,
                             final Optional<Integer> lengthOptional,
                             final Optional<Integer> widthOptional,
                             final Optional<Integer> heightOptional,
                             final Cargo.DimensionUnit dimensionUnit,
                             final Optional<ContainerType> containerTypeOptional,
                             final Optional<BulkType> bulkTypeOptional) {
        getSessionProvider().startTransaction();
        final Cargo cargo = new Cargo.Builder()
                .userId(userId)
                .cargoType(cargoType)
                .quantity(quantity)
                .originLocation(originLocation)
                .destinationLocation(destinationLocation)
                .departure(departure)
                .weight(weightOptional.map(weight -> weight).orElse(null))
                .weightUnit(weightUnit)
                .volume(volumeOptional.map(volume -> volume).orElse(null))
                .volumeUnit(volumeUnit)
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

    public List<Cargo> getByUserIdAndStatusListSortedAndPaginated(final int userId,
                                                                  final List<Cargo.Status> statusList,
                                                                  final int start,
                                                                  final int limit) {
        requireNonNull(statusList);
        if (isEmpty(statusList)) {
            return emptyList();
        }

        final Map<String, Object> inputParam = new HashMap<>();
        inputParam.put("userId", userId);
        inputParam.put("status", statusList);

        return getByFieldSortedAndPaginated("userId = :userId AND status IN :status", inputParam, "id", DESC, start, limit);
    }

    public void updateCargoStatusShipmentIdAndContractId(final int cargoId,
                                                         final Cargo.Status status,
                                                         final int shipmentId,
                                                         final int contractId) {
        assertNotNull(status, STATUS_NOT_EXIST);
        final Query query = getSessionProvider().getSession().createQuery(
                "UPDATE " + clazz.getName() + " SET status = :status, shipmentId = :shipmentId, contractId = :contractId WHERE id = :cargoId");
        query.setParameter("cargoId", cargoId);
        query.setParameter("status", status);
        query.setParameter("shipmentId", shipmentId);
        query.setParameter("contractId", contractId);
        query.executeUpdate();
    }
}
