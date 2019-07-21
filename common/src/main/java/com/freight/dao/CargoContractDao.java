package com.freight.dao;

import com.freight.model.CargoContract;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.freight.dao.BaseDao.Sort.DESC;

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
}
