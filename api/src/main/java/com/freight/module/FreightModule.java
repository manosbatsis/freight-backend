package com.freight.module;

import com.google.inject.AbstractModule;

/**
 * Created by toshikijahja on 3/23/19.
 */
public class FreightModule extends AbstractModule {

    @Override
    public void configure() {
        // DB, Dao
        install(new PersistenceModule());
        install(new DaoProviderModule());
    }
}
