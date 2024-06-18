package com.traineeproject.core.servlets;

import com.adobe.aemfd.docmanager.Document;
import com.adobe.fd.output.api.AcrobatVersion;
import com.adobe.fd.output.api.OutputService;
import com.adobe.fd.output.api.OutputServiceException;
import com.adobe.fd.output.api.PDFOutputOptions;
import com.traineeproject.core.utils.XmlParserData;
import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.apache.sling.api.servlets.ServletResolverConstants.*;

@Component(service = {Servlet.class}, immediate = true,
        property = { SLING_SERVLET_METHODS + "=POST",
                SLING_SERVLET_EXTENSIONS + "=json",
                SLING_SERVLET_PATHS + "=/bin/generatePDFAServiceTest"})
public class PDFATest extends SlingAllMethodsServlet {
    private static final String XDP_TEMPLATE = "";
    private static final String JCR_PATH_URL = "";

    private static final String PATH_URL = "";

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratePDFAService.class);


    @Reference
    private OutputService outputService;

    @Override
    protected void doPost (SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("===============START post================");

        try {
            String xmlInput = "";
            // getting XML input data and converting into doc object
    // getting XML input data and converting into doc object
    String xmlData = request.getParameter("xmlData");
            LOGGER.info("xmlData = " + xmlData);
            if (xmlData.endsWith(".xml")) {
        String xmlFile = new String(Files.readAllBytes(Paths.get(xmlData)));
        xmlInput = XmlParserData.parseXmpDataDoc(xmlFile);

    } else {
        xmlInput = XmlParserData.parseXmpDataDoc(xmlData);
    }
            LOGGER.info("get data");
            LOGGER.info("xmlInput = " + xmlInput);


        LOGGER.info("xmlData= " + xmlData);
    InputStream xmlIS = IOUtils.toInputStream(xmlInput, StandardCharsets.UTF_8);
    Document xmlDocument = new Document(xmlIS);

//    PDFOutputOptions outputOptions = new PDFOutputOptions();
//        outputOptions.setAcrobatVersion(AcrobatVersion.Acrobat_11);
//        outputOptions.setContentRoot(JCR_PATH_URL);
    //Generate OutputOption
    PDFOutputOptions outputOptions = new PDFOutputOptions();
        outputOptions.setAcrobatVersion(AcrobatVersion.Acrobat_10);
        outputOptions.setContentRoot(PATH_URL);

    //Generate PDF output with a xdp and xml input.
    Document doc = outputService.generatePDFOutput(XDP_TEMPLATE, xmlDocument, outputOptions);
        LOGGER.info("Generated doc= " + doc);



    //Save result in pdf file
//    File pdfFile = new File("traineeproject/core/files/GeneratedPDF.pdf");
    File pdfFile = new File(PATH_URL +"/GeneratedPDF.pdf");
            doc.copyToFile(pdfFile);

        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename=output.pdf");

    OutputStream responseOutputStream = response.getOutputStream();
            IOUtils.copy(doc.getInputStream(), responseOutputStream);

            responseOutputStream.flush();
            responseOutputStream.close();

} catch (OutputServiceException e) {
        e.getStackTrace();
        response.getWriter().write("OutputServiceException occurred: PDF generation failed. " + e.getMessage());
        } catch (Exception e) {
        e.printStackTrace();
        response.getWriter().write("Exception occurred: " + e.getMessage());
        }




        }

        }
