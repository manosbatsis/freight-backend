package com.freight.dao;

import com.freight.model.CargoType;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

import static java.util.Collections.emptyList;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class CargoTypeDao extends BaseDao<CargoType> {

    @AssistedInject
    public CargoTypeDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, CargoType.class);
    }

    public List<CargoType> getByTypes(final List<String> cargoTypes) {
        if (CollectionUtils.isEmpty(cargoTypes)) {
            return emptyList();
        }

        return getByFieldStringList("type", cargoTypes);
    }
}
