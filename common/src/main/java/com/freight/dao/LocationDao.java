package com.freight.dao;

import com.freight.model.Location;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class LocationDao extends BaseDao<Location> {

    @AssistedInject
    public LocationDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, Location.class);
    }
}
