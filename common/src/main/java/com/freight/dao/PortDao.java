package com.freight.dao;

import com.freight.model.Port;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class PortDao extends BaseDao<Port> {

    @AssistedInject
    public PortDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, Port.class);
    }
}
