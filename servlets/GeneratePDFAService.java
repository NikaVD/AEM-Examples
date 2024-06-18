package com.traineeproject.core.servlets;

import com.adobe.fd.output.api.OutputService;
import com.traineeproject.core.utils.CreatePDFADocument;
import com.traineeproject.core.utils.XmlParserData;
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
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.apache.sling.api.servlets.ServletResolverConstants.*;

@Component(service = {Servlet.class}, immediate = true,
property = { SLING_SERVLET_METHODS + "=POST",
        SLING_SERVLET_EXTENSIONS + "=json",
        SLING_SERVLET_PATHS + "=/bin/generatePDFAService"})
public class GeneratePDFAService extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratePDFAService.class);

    @Reference
    private OutputService outputService;

    @Reference
    private CreatePDFADocument createPDFADocument;

    @Override
    protected void doPost (SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("===============START post PDF/A Service================");

        try {
            org.w3c.dom.Document xmlInput;
            // getting XML input data and converting into doc object
            String xmlData = request.getParameter("dataXml");
            LOGGER.info("xmlData = " + xmlData);
            if (xmlData.endsWith(".xml")) {
                String xmlFile = new String(Files.readAllBytes(Paths.get(xmlData)));
                xmlInput = XmlParserData.parseXmpData(xmlFile);

            } else {
                xmlInput = XmlParserData.parseXmpData(xmlData);
            }



            CreatePDFADocument.createPDFADocument(xmlInput);

            LOGGER.info("Create PDF/A Doc");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Exception occurred: " + e.getMessage());
        }




    }

}
