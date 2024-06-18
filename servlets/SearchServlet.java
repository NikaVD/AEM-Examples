package com.traineeproject.core.servlets;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.google.gson.Gson;
import com.traineeproject.core.services.SearchPathService;
import com.traineeproject.core.services.pojo.File;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(service = {Servlet.class})
@SlingServletResourceTypes(
        resourceTypes = "sling/servlet/default",
        selectors = "query123",
        extensions = "json",
        methods = HttpConstants.METHOD_GET
)
@ServiceDescription("Search API")
public class SearchServlet extends SlingSafeMethodsServlet {
    @Reference
    SearchPathService searchPathService;

    List<String> listFiles;


    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        Gson gson = new Gson();

//        listFiles = searchPathService.fileList("/content/traineeproject/us", request.getResourceResolver());

        response.getWriter().write(gson.toJson(listFiles));
        response.getWriter().close();


    }


}
