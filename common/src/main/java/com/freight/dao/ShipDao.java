package com.freight.dao;

import com.freight.model.Company;
import com.freight.model.Ship;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import static com.freight.exception.BadRequest.COMPANY_NOT_EXIST;
import static com.freight.exception.BadRequest.SHIP_NAME_NOT_EXIST;
import static com.freight.util.AssertUtil.assertNotNull;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class ShipDao extends BaseDao<Ship> {

    @AssistedInject
    public ShipDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, Ship.class);
    }

    public Ship createShip(final String name,
                           final Company company,
                           final Integer yearBuilt,
                           final Integer grossTonnage) {
        assertNotNull(name, SHIP_NAME_NOT_EXIST);
        assertNotNull(company, COMPANY_NOT_EXIST);
        getSessionProvider().startTransaction();
        final Ship ship = new Ship.Builder()
                .name(name)
                .company(company)
                .yearBuilt(yearBuilt)
                .grossTonnage(grossTonnage)
                .build();
        getSessionProvider().getSession().persist(ship);
        getSessionProvider().commitTransaction();
        return ship;
    }
}
