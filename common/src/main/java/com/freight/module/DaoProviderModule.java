package com.freight.module;

import com.freight.persistence.DaoFactory;
import com.freight.persistence.DaoProvider;
import com.freight.persistence.PersistenceDirector;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Created by toshikijahja on 6/16/19.
 */
public class DaoProviderModule extends AbstractModule {
    private static volatile DaoProvider daoProvider;

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder().build(DaoFactory.class));
    }

    @Provides
    protected final DaoProvider getDaoProvider(final PersistenceDirector persistenceDirector,
                                               final DaoFactory daoFactory) {
        if (daoProvider == null) {
            synchronized (DaoProviderModule.class) {
                if (daoProvider == null) {
                    daoProvider = new DaoProvider(persistenceDirector, daoFactory);
                }
            }
        }
        return daoProvider;
    }
}
