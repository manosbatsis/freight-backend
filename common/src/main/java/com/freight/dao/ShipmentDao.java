package com.freight.dao;

import com.freight.model.Location;
import com.freight.model.Ship;
import com.freight.model.Shipment;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.freight.dao.BaseDao.Sort.DESC;
import static com.freight.exception.BadRequest.DESTINATION_PORT_EMPTY;
import static com.freight.exception.BadRequest.ORIGIN_PORT_EMPTY;
import static com.freight.exception.BadRequest.SHIP_NOT_EXIST;
import static com.freight.util.AssertUtil.assertNotNull;
import static io.jsonwebtoken.lang.Collections.isEmpty;
import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class ShipmentDao extends BaseDao<Shipment> {

    @AssistedInject
    public ShipmentDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, Shipment.class);
    }

    public List<Shipment> getByStatus(final Shipment.Status status) {
        return getByField("status", status);
    }

    public Shipment createShipment(final Ship ship,
                                   final Location originLocation,
                                   final Location destinationLocation,
                                   final long departure,
                                   final long arrival) {
        assertNotNull(ship, SHIP_NOT_EXIST);
        assertNotNull(originLocation, ORIGIN_PORT_EMPTY);
        assertNotNull(destinationLocation, DESTINATION_PORT_EMPTY);
        getSessionProvider().startTransaction();
        final Shipment shipment = new Shipment.Builder()
                .ship(ship)
                .originLocation(originLocation)
                .destinationLocation(destinationLocation)
                .departure(Instant.ofEpochSecond(departure))
                .arrival(Instant.ofEpochSecond(arrival))
                .build();
        getSessionProvider().getSession().persist(shipment);
        getSessionProvider().commitTransaction();
        return shipment;
    }

    public List<Shipment> getByIdsAndStatusListSortedAndPaginated(final List<Integer> ids,
                                                                  final List<Shipment.Status> statusList,
                                                                  final int start,
                                                                  final int limit) {
        requireNonNull(statusList);
        if (isEmpty(ids) || isEmpty(statusList)) {
            return emptyList();
        }

        final Map<String, Object> inputParam = new HashMap<>();
        inputParam.put("id", ids);
        inputParam.put("status", statusList);

        return getByFieldSortedAndPaginated("id IN :id AND status IN :status", inputParam, "id", DESC, start, limit);
    }
}
