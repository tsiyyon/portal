package com.morpheus.portal;

import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

public class PortalFeature implements Feature {
    @Override
    public boolean configure(FeatureContext context) {

        boolean registered = false;
        final Configuration config = context.getConfiguration();
        if (!config.isRegistered(RequestBodyReader.class)) {
            context.register(RequestBodyReader.class);
            registered = true;
        }

        if (!config.isRegistered(JacksonFeature.class)) {
            context.register(JacksonFeature.class);
            registered = true;
        }

        return registered;
    }
}
