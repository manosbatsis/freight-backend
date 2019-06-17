package com.freight.persistence;

import com.freight.dao.SessionProvider;
import com.google.inject.Inject;

/**
 * Created by toshikijahja on 6/16/19.
 */
public class DaoProvider {

    private final PersistenceDirector persistenceDirector;
    private final DaoFactory daoFactory;

    @Inject
    public DaoProvider(final PersistenceDirector persistenceDirector,
                       final DaoFactory daoFactory) {
        this.persistenceDirector = persistenceDirector;
        this.daoFactory = daoFactory;
    }

    public DaoFactory getDaoFactory() {
        return daoFactory;
    }

    public SessionProvider getSessionProvider() {
        return persistenceDirector.getSessionProvider();
    }
}
