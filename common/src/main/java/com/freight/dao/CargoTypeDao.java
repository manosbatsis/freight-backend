package com.freight.dao;

import com.freight.model.CargoType;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.freight.util.QueryUtil.listStringToSqlQuery;
import static java.util.Collections.emptyList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class CargoTypeDao extends BaseDao<CargoType> {

    @AssistedInject
    public CargoTypeDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, CargoType.class);
    }

    public List<CargoType> getByTypes(final List<String> cargoTypes) {
        if (isEmpty(cargoTypes)) {
            return emptyList();
        }

        final Map<String, Object> inputParam = new HashMap<>();
        inputParam.put("type", listStringToSqlQuery(cargoTypes));

        return getByFields("type IN :type", inputParam);
    }
}
