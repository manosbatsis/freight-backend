package com.freight;

/**
 * Created by toshikijahja on 3/23/19.
 */

import com.freight.auth.UserScope;
import com.freight.guice.ServiceLocatorContainer;
import com.freight.module.FreightModule;
import com.google.inject.Scopes;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.HashMap;
import java.util.Map;


public class FreightJerseyModule extends ServletModule {

    @Override
    protected void configureServlets() {
        super.configureServlets();
        bind(ServiceLocator.class).toProvider(ServiceLocatorContainer.class);

        final Map<String, String> params = new HashMap<>();
        params.put("javax.ws.rs.Application", FreightApplication.class.getCanonicalName());
        bind(ServletContainer.class).in(Scopes.SINGLETON);
        serve("/*").with(ServletContainer.class, params);
        bind(UserScope.class).in(RequestScoped.class);

        install(new FreightModule());
    }
}
