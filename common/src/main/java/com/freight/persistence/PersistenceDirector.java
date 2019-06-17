package com.freight.persistence;

import com.freight.dao.SessionProvider;
import com.google.inject.Inject;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by toshikijahja on 6/16/19.
 */
public class PersistenceDirector {

    private SessionFactory sessionFactory;

    @Inject
    public PersistenceDirector() {
        final Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    public SessionProvider getSessionProvider(){
        return new SessionProvider(sessionFactory.openSession());
    }
}
