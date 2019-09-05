package com.freight.dao;

import com.freight.model.Facility;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class FacilityDao extends BaseDao<Facility> {

    @AssistedInject
    public FacilityDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, Facility.class);
    }
}
