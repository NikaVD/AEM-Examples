package com.traineeproject.core.utils;


import com.itextpdf.text.pdf.PdfAConformanceLevel;
import com.itextpdf.text.pdf.PdfAStamper;
import com.itextpdf.text.pdf.PdfReader;
import com.traineeproject.core.servlets.GeneratePDFAService;
import org.apache.log4j.FileAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class EmbededXml extends FileAppender {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratePDFAService.class);

    public static void AddEmbeddedFile (String SRC, String DEST, Document xmlInput) throws Exception {
        LOGGER.info("================AddEmbedded Start==============");
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        manipulatePdf(SRC, DEST, xmlInput);
    }


    public static void manipulatePdf(String src, String dest, Document xmlInput) throws Exception {
//        LOGGER.info("================manipulatePdf Start_xml Embedded==============");
//
//        PdfReader reader = new PdfReader(src);
//        PdfAStamper stamper = new PdfAStamper(reader, new FileOutputStream(dest), PdfAConformanceLevel.PDF_A_1A);
//        PdfFileSpecification fs = PdfFileSpecification.fileEmbedded(
//                stamper.getWriter(), null, "data.xml", asByteArray(xmlInput), true);
//        stamper.addFileAttachment("data file", fs);
//        stamper.close();
//        LOGGER.info("================manipulatePdf Finish_xml Embedded==============");

        LOGGER.info("================manipulatePdf Start_xmp Metadata==============");
        PdfReader reader = new PdfReader(src);
        PdfAStamper stamper = new PdfAStamper(reader, new FileOutputStream(dest), PdfAConformanceLevel.PDF_A_1A);

        String metadata = new String(reader.getMetadata());
        StringBuilder stringBuilder = new StringBuilder();

        NodeList list = xmlInput.getElementsByTagName("*");

        for (int i = 1; i < list.getLength(); i++) {
            org.w3c.dom.Element element = (org.w3c.dom.Element) list.item(i);
            stringBuilder.append("\n" + element.getNodeName() + "=" + "\"" + xmlInput.getElementsByTagName(element.getNodeName()).item(0).getTextContent() + "\"");

        }

        LOGGER.info("======Builder======"+ "\n" + stringBuilder);
//
        stamper.setXmpMetadata(metadata.replace("pdfaid:part=\"1\"", "pdfaid:part=\"1\"" + stringBuilder).getBytes());
        stamper.close();

        LOGGER.info("================manipulatePdf Finish_xmp Metadata==============");
    }

    public static byte[] asByteArray(Document xmlInput)
            throws TransformerException, IOException, ParserConfigurationException, SAXException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        transformer = transformerFactory.newTransformer();
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(bout);
        DOMSource source = new DOMSource(xmlInput);
        transformer.transform(source, result);
        return bout.toByteArray();

    }

}
