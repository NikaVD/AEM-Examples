package com.traineeproject.core.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.traineeproject.core.services.DemoFilterService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.engine.EngineConstants;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.apache.sling.engine.EngineConstants;
import org.osgi.service.component.annotations.Component;

@Component(service = Filter.class,
        property = {
                EngineConstants.SLING_FILTER_SCOPE + "=" + EngineConstants.FILTER_SCOPE_REQUEST,
                Constants.SERVICE_DESCRIPTION + "=Demo filter requests",
                Constants.SERVICE_RANKING + ":Integer=-700",
                "sling.filter.pattern=" + "/content/we-retail/.*"
        })
public class DemoFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(DemoFilter.class);

    @Reference
    DemoFilterService demoFilterService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        demoFilterService.replace(servletRequest, servletResponse, filterChain);

    }

    @Override
    public void destroy() {

    }
}
