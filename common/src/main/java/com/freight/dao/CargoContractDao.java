package com.freight.dao;

import com.freight.model.CargoContract;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.hibernate.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.freight.dao.BaseDao.Sort.DESC;
import static com.freight.exception.BadRequest.STATUS_NOT_EXIST;
import static com.freight.util.AssertUtil.assertNotNull;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class CargoContractDao extends BaseDao<CargoContract> {

    @AssistedInject
    public CargoContractDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, CargoContract.class);
    }

    public List<CargoContract> getByCargoIdSortedAndPaginated(final int cargoId,
                                                              final int start,
                                                              final int limit) {

        final Map<String, Object> inputParam = new HashMap<>();
        inputParam.put("cargoId", cargoId);

        return getByFieldSortedAndPaginated("cargoId =: cargoId", inputParam, "contractId", DESC, start, limit);
    }

    public Optional<CargoContract> getByContractId(final int contractId) {
        return getByFieldOptional("contractId", contractId);
    }

    public void updateStatusByContractId(final CargoContract.Status status, final int contractId) {
        assertNotNull(status, STATUS_NOT_EXIST);
        final Query query = getSessionProvider().getSession().createQuery(
                "UPDATE " + clazz.getName() + " SET status = :status WHERE contractId = :contractId");
        query.setParameter("status", status);
        query.setParameter("contractId", contractId);
        query.executeUpdate();
    }

    public void updateStatusByCargoIdExcludeContractId(final CargoContract.Status status,
                                                       final int cargoId,
                                                       final int contractId) {
        assertNotNull(status, STATUS_NOT_EXIST);
        final Query query = getSessionProvider().getSession().createQuery(
                "UPDATE " + clazz.getName() + " SET status = :status WHERE cargoId = :cargoId AND contractId != :contractId");
        query.setParameter("status", status);
        query.setParameter("cargoId", cargoId);
        query.setParameter("contractId", contractId);
        query.executeUpdate();
    }
}
