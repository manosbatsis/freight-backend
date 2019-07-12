package com.freight.dao;

import com.freight.model.Cargo;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Created by toshikijahja on 6/7/17.
 */
public class ContractDao extends BaseDao<Cargo> {

    @AssistedInject
    public ContractDao(@Assisted final SessionProvider sessionProvider) {
        super(sessionProvider, Cargo.class);
    }
}
