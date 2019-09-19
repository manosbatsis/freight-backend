package com.freight.dao;

import com.freight.model.Facility;
import com.freight.model.ShipFacility;
import com.freight.request_body.ShipFacilityRequestBody;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class ShipFacilityDao extends BaseDao<ShipFacility> {

    @AssistedInject
    public ShipFacilityDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, ShipFacility.class);
    }

    public List<ShipFacility> createShipFacilities(final int shipId,
                                                   final List<ShipFacilityRequestBody> shipFacilityRequestBodies,
                                                   final Map<Integer, Facility> facilityById) {
        if (shipFacilityRequestBodies.isEmpty()) {
            return emptyList();
        }
        getSessionProvider().startTransaction();
        final List<ShipFacility> shipFacilities = shipFacilityRequestBodies.stream().map(shipFacility -> {
            final ShipFacility shipCargoType = new ShipFacility.Builder()
                    .facility(facilityById.get(shipFacility.getFacilityId()))
                    .shipId(shipId)
                    .description(shipFacility.getDescriptionOptional().isPresent()
                            ? shipFacility.getDescriptionOptional().get() : null)
                    .build();
            getSessionProvider().getSession().persist(shipCargoType);
            return shipCargoType;
        }).collect(toList());
        getSessionProvider().commitTransaction();
        return shipFacilities;
    }

    public List<ShipFacility> getByShipId(final int shipId) {
        return getByShipIds(singletonList(shipId));
    }

    public List<ShipFacility> getByShipIds(final List<Integer> shipIds) {
        if (shipIds.isEmpty()) {
            return emptyList();
        }
        final Map<String, Object> inputParams = new HashMap<>();
        inputParams.put("shipIds", shipIds);
        return getByFields("shipId IN :shipIds", inputParams);
    }
}
