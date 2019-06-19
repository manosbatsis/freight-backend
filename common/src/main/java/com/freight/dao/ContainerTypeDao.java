package com.freight.dao;

import com.freight.model.ContainerType;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class ContainerTypeDao extends BaseDao<ContainerType> {

    @AssistedInject
    public ContainerTypeDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, ContainerType.class);
    }
}
