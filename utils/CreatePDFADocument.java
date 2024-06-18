package com.traineeproject.core.utils;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.traineeproject.core.servlets.GeneratePDFAService;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;

import java.io.*;

@Component(service = CreatePDFADocument.class, immediate = true)
public class CreatePDFADocument {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratePDFAService.class);

    private static String FILE_LOCATION = "/Users/verdav/Documents/vvd/aem_test/test.pdf";
    private static String FILE_LOCATION_WITH_EMBEDDED = "/Users/verdav/Documents/vvd/aem_test/test_attachment_embedded.pdf";

    private static String FILE_LOCATION_WITH_XMP_METADATA = "/Users/verdav/Documents/vvd/aem_test/test_xmp_metadata.pdf";
    private static String COLOR_PROFILE = "/Users/verdav/traineeproject/core/src/main/java/files/colorProfile/sRGB_CS_profile.icm";
    private static String FONT_PROFILE = "/Users/verdav/traineeproject/core/src/main/java/files/font/OpenSans-Regular.ttf";

    static final float MARGIN_OF_ONE_CM = 28.8f;


    public static void createPDFADocument (org.w3c.dom.Document xmlInput) throws Exception {
        LOGGER.info("=========Start PDF/A Writer=========");

        Document document = new Document(PageSize.A4
                , MARGIN_OF_ONE_CM
                , MARGIN_OF_ONE_CM
                , MARGIN_OF_ONE_CM
                , MARGIN_OF_ONE_CM);

        try {
            PdfAWriter writer = PdfAWriter.getInstance(document, new FileOutputStream(FILE_LOCATION), PdfAConformanceLevel.PDF_A_1A);
            LOGGER.info(FILE_LOCATION);
            File colorProfile = new File(COLOR_PROFILE);
            ICC_Profile icc = ICC_Profile.getInstance(new FileInputStream(colorProfile));
            String FONT = FONT_PROFILE;
            Font font = new Font(BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 10);

            final PdfDictionary extraCatalog = writer.getExtraCatalog();
            final PdfDictionary markInfoDict = new PdfDictionary();
            markInfoDict.put(PdfName.MARKED, new PdfBoolean(true));
            extraCatalog.put(PdfName.MARKINFO, markInfoDict);
            extraCatalog.put(PdfName.LANG, new PdfString("EN"));


            document.open();
            writer.createXmpMetadata();
            writer.setOutputIntents("Custom", "", "http://www.color.org", "sRGB IEC61966-2.1", icc);

            /////

            /*
            STEP3_Write:
                - create Element (always define Font with embedded one)
                - add Element to document
            */
            PdfPTable table = new PdfPTable(2);
            for (int i = 0; i < 2; i++) {
                PdfPCell cell = new PdfPCell(new Phrase("Header " + i, font));
                cell.setBackgroundColor(new BaseColor(0,255,255));
                table.addCell(cell);
            }
            table.setHeaderRows(1);

            NodeList list = xmlInput.getElementsByTagName("*");

            for (int i = 1; i < list.getLength(); i++) {
                org.w3c.dom.Element element = (org.w3c.dom.Element) list.item(i);
                table.addCell(new Phrase(element.getNodeName(), font));
                table.addCell(new Phrase(xmlInput.getElementsByTagName(element.getNodeName()).item(0).getTextContent(), font));
            }


            document.add(table);
            LOGGER.info("Add table");

        } catch (FileNotFoundException e) {
            LOGGER.info("FileNotFoundException");
            e.printStackTrace();
        } catch (DocumentException e) {
            LOGGER.info("DocumentException");
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.info("IOException");
            e.printStackTrace();
        } finally {
            LOGGER.info("finally");
            /*
            STEP4_Close:
                - close inside finally, in order to not create a zombie
            */
            document.close();
            LOGGER.info("Document close");





//            EmbededXml.AddEmbeddedFile(FILE_LOCATION, FILE_LOCATION_WITH_EMBEDDED, xmlInput);
            EmbededXml.AddEmbeddedFile(FILE_LOCATION, FILE_LOCATION_WITH_XMP_METADATA, xmlInput);
        }



    }



}
