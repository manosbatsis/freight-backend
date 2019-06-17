package com.freight.module;

import com.freight.persistence.PersistenceDirector;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * Created by toshikijahja on 6/16/19.
 */
public class PersistenceModule extends AbstractModule {

    private static volatile PersistenceDirector persistenceDirector;

    @Override
    protected void configure() {

    }

    @Provides
    public static PersistenceDirector getPersistenceDirector() {
        if (persistenceDirector == null) {
            synchronized (PersistenceModule.class) {
                if (persistenceDirector == null) {
                    persistenceDirector = new PersistenceDirector();
                }
            }
        }
        return persistenceDirector;
    }
}
