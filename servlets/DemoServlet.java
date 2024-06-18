package com.traineeproject.core.servlets;

import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.traineeproject.core.services.DemoServletService;
import org.apache.jackrabbit.vault.util.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.json.JSONException;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class,
        property = { Constants.SERVICE_DESCRIPTION + "=Servlet to call API Url",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.resourceTypes=" + "weretail/components/structure/page",
        "sling.servlet.selectors="+"listchildren"})
@ServiceDescription("Demo Servlet return JSON")
public class DemoServlet extends SlingAllMethodsServlet {
    final String CONTENT_TYPE = "application/json";
    @Reference
    DemoServletService demoServletService;

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
            throws ServletException, IOException {
        final Resource resource = req.getResource();

        ResourceResolver resourceResolver = req.getResourceResolver();

        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding("UTF-8");
        try {
            resp.getWriter().write(demoServletService.getJsonFile(resource.getParent()));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
}
