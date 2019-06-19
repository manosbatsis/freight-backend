package com.freight.dao;

import com.freight.model.Port;
import com.freight.model.Ship;
import com.freight.model.Shipment;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import java.time.Instant;
import java.util.List;

import static com.freight.exception.BadRequest.DESTINATION_PORT_EMPTY;
import static com.freight.exception.BadRequest.ORIGIN_PORT_EMPTY;
import static com.freight.exception.BadRequest.SHIP_NOT_EXIST;
import static com.freight.util.AssertUtil.assertNotNull;

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
                                   final Port originPort,
                                   final Port destinationPort,
                                   final long estimatedDeparture,
                                   final long estimatedArrival) {
        assertNotNull(ship, SHIP_NOT_EXIST);
        assertNotNull(originPort, ORIGIN_PORT_EMPTY);
        assertNotNull(destinationPort, DESTINATION_PORT_EMPTY);
        getSessionProvider().startTransaction();
        final Shipment shipment = new Shipment.Builder()
                .ship(ship)
                .originPort(originPort)
                .destinationPort(destinationPort)
                .estimatedDeparture(Instant.ofEpochSecond(estimatedDeparture))
                .estimatedArrival(Instant.ofEpochSecond(estimatedArrival))
                .build();
        getSessionProvider().getSession().persist(shipment);
        getSessionProvider().commitTransaction();
        return shipment;
    }
}
