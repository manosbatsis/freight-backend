package com.freight.dao;

import com.freight.model.BulkType;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class BulkTypeDao extends BaseDao<BulkType> {

    @AssistedInject
    public BulkTypeDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, BulkType.class);
    }
}
