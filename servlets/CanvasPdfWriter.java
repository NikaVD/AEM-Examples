package com.traineeproject.core.servlets;

import com.traineeproject.core.utils.CreatePDFDocument;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

import static com.traineeproject.core.utils.CreatePDFDocument.createEmailText;
import static com.traineeproject.core.utils.CreatePDFDocument.createPDFDocument;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_METHODS;
import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_PATHS;

@Component(service = {Servlet.class}, immediate = true,
        property = { SLING_SERVLET_METHODS + "=GET",
                SLING_SERVLET_PATHS + "=/bin/canvasproject/servlet"})

public class CanvasPdfWriter extends SlingAllMethodsServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CanvasPdfWriter.class);
    private static final long serialVersionUID = 1L;

    @Reference
    private CreatePDFDocument createPDFDocument;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("===========Start Servlet==================");
        String jsonProperties = request.getParameter("jsonProp");
        String title = request.getParameter("title");

        try {
            createPDFDocument(jsonProperties, title);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        try {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(createEmailText(jsonProperties, title).toString());
        } catch (Exception e) {
            LOGGER.error("Something ridiculous has happened !! {}" , e);
        }

        LOGGER.trace("Sabya Test Servlet : doGet() ends .. ");


        LOGGER.info("===========file was saved===============");

    }

}
