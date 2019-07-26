package com.freight.dao;

import com.freight.model.CargoShipment;
import com.freight.model.Shipment;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.freight.dao.BaseDao.Sort.DESC;
import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class CargoShipmentDao extends BaseDao<CargoShipment> {

    @AssistedInject
    public CargoShipmentDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, CargoShipment.class);
    }

    public List<CargoShipment> getByUserIdAndShipmentStatusListSortedAndPaginated(final int userId,
                                                                                  final List<Shipment.Status> statusList,
                                                                                  final int start,
                                                                                  final int limit) {
        requireNonNull(statusList);
        if (isEmpty(statusList)) {
            return emptyList();
        }

        final Map<String, Object> inputParam = new HashMap<>();
        inputParam.put("userId", userId);
        inputParam.put("status", statusList);

        return getByFieldSortedAndPaginated("cargo.userId = :userId AND shipment.status IN :status", inputParam, "shipment.id", DESC, start, limit);
    }
}
