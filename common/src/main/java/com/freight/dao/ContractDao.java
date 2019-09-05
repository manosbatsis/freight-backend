package com.freight.dao;

import com.freight.model.Contract;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.hibernate.query.Query;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class ContractDao extends BaseDao<Contract> {

    @AssistedInject
    public ContractDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, Contract.class);
    }

    public void updateContractShipmentId(final int contractId, final int shipmentId) {
        final Query query = getSessionProvider().getSession().createQuery(
                "UPDATE " + clazz.getName() + " SET shipmentId = :shipmentId WHERE id = :contractId");
        query.setParameter("contractId", contractId);
        query.setParameter("shipmentId", shipmentId);
        query.executeUpdate();
    }
}
