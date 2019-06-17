package com.freight.auth;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

/**
 * Created by toshikijahja on 6/15/19.
 */
public class SecurityFilterBindingFeature implements DynamicFeature {
    @Override
    public void configure(final ResourceInfo resourceInfo, final FeatureContext featureContext) {
        if (resourceInfo.getResourceMethod().isAnnotationPresent(UserAuth.class)) {
            featureContext.register(UserFilter.class);
        }
    }
}
