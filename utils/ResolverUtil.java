package com.traineeproject.core.utils;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import java.util.HashMap;
import java.util.Map;

public final class ResolverUtil {

    private ResolverUtil() {

    }

    public static final String TRAINEE_SYSTEM_USER = "traineeSystemUser";

    public static ResourceResolver newResolver (ResourceResolverFactory resourceResolverFactory ) throws LoginException {
        final Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(ResourceResolverFactory.SUBSERVICE, TRAINEE_SYSTEM_USER);
        ResourceResolver resolver = resourceResolverFactory.getServiceResourceResolver(paramMap);


        return resolver;
    }
}
