package com.freight.dao;

import com.freight.model.Location;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Objects.nonNull;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class LocationDao extends BaseDao<Location> {

    @AssistedInject
    public LocationDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, Location.class);
    }

    public Location getOrCreateIfNotExist(final String externalId,
                                          final String mainName,
                                          final String secondaryName,
                                          final BigDecimal lat,
                                          final BigDecimal lon,
                                          final String route,
                                          final String locality,
                                          final String village,
                                          final String subdistrict,
                                          final String city,
                                          final String province,
                                          final String country) {
        nonNull(externalId);
        nonNull(mainName);
        nonNull(secondaryName);
        nonNull(lat);
        nonNull(lon);
        final Optional<Location> locationOptional = getByFieldOptional("externalId", externalId);
        if (locationOptional.isPresent()) {
            return locationOptional.get();
        }

        return createLocation(externalId, mainName, secondaryName, lat, lon, route,
                locality, village, subdistrict, city, province, country);
    }

    public Location createLocation(final String externalId,
                                   final String mainName,
                                   final String secondaryName,
                                   final BigDecimal lat,
                                   final BigDecimal lon,
                                   final String route,
                                   final String locality,
                                   final String village,
                                   final String subdistrict,
                                   final String city,
                                   final String province,
                                   final String country) {
        nonNull(externalId);
        nonNull(mainName);
        nonNull(secondaryName);
        nonNull(lat);
        nonNull(lon);
        getSessionProvider().startTransaction();
        final Location location = new Location.Builder()
                .externalId(externalId)
                .mainName(mainName)
                .secondaryName(secondaryName)
                .lat(lat)
                .lon(lon)
                .route(route)
                .locality(locality)
                .village(village)
                .subdistrict(subdistrict)
                .city(city)
                .province(province)
                .country(country)
                .build();
        getSessionProvider().getSession().persist(location);
        getSessionProvider().commitTransaction();
        return location;
    }

}
