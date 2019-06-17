package com.freight;

import com.freight.auth.SecurityFilterBindingFeature;
import com.freight.exception.FreightExceptionMapper;
import com.freight.guice.FreightGuiceBridge;
import com.freight.guice.ServiceLocatorContainer;
import com.google.inject.Injector;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import javax.inject.Inject;

/**
 * Created by toshikijahja on 3/23/19.
 */
public class FreightApplication extends ResourceConfig {

    private static final String RESOURCE_PACKAGE = "com.freight.resource";

    @Inject
    public FreightApplication(final ServiceLocator serviceLocator) {
        System.out.println("Configuring Application");

        // Guice
        System.out.println("Initializing the guice bridge");
        FreightGuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        final GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
        final Injector injector = FreightServletConfig.INSTANCE;
        injector.getInstance(ServiceLocatorContainer.class).setServiceLocator(serviceLocator);
        guiceBridge.bridgeGuiceInjector(injector);

        packages(RESOURCE_PACKAGE);

        // Jackson
        register(ObjectMapperProvider.class);
        register(JacksonFeature.class);

        register(FreightExceptionMapper.class);

        // Security filter
        register(SecurityFilterBindingFeature.class);

//        final SharedConfig sharedConfig = injector.getInstance(SharedConfig.class);
//        if (!sharedConfig.getEnv().equals("production")) {

        // Swagger
        register(ApiListingResource.class);
        register(SwaggerSerializers.class);
        swaggerSetup();
//        }

//        register(CORSFilter.class);
//        register(FreightApplicationEventListener.class);
    }

    private BeanConfig swaggerSetup() {
        final BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setResourcePackage(RESOURCE_PACKAGE);
        beanConfig.setBasePath("/");
        beanConfig.setDescription("Freight API Documentation");
        beanConfig.setTitle("Freight API");
        beanConfig.setScan(true);
        return beanConfig;
    }
}
