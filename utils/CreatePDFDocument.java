package com.traineeproject.core.utils;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.traineeproject.core.servlets.CanvasPdfWriter;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

@Component(service = CreatePDFDocument.class, immediate = true)
public class CreatePDFDocument {
    private static final Logger LOGGER = LoggerFactory.getLogger(CanvasPdfWriter.class);

    private static String FILE_LOCATION = "/Users/verdav/Documents/vvd/aem_test/pdf.pdf";


    static final float MARGIN_OF_ONE_CM = 28.8f;


    public static void createPDFDocument (String inputString, String title) throws Exception {
        LOGGER.info("=========Start PDF Writer=========");

        Document document = new Document(PageSize.LETTER
                , MARGIN_OF_ONE_CM
                , MARGIN_OF_ONE_CM
                , MARGIN_OF_ONE_CM
                , MARGIN_OF_ONE_CM);

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE_LOCATION));
            LOGGER.info(FILE_LOCATION);

            document.open();
            document.add(new Paragraph(title));
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(2);
            for (int i = 0; i < 2; i++) {
                table.addCell("");
            }
            table.setHeaderRows(1);

            String[] inputList = inputString.replace("\"", "").split(",");

            for (int i = 0; i < inputList.length; i++) {
                table.addCell(String.valueOf(i));
                table.addCell(Constants.getProperties(inputList[i]));
            }


            document.add(table);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
            LOGGER.info("Document close");

        }

    }

    public static StringBuilder createEmailText (String inputString, String title) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(title + ": ");
        stringBuilder.append(System.getProperty("line.separator"));

        String[] inputList = inputString.replace("\"", "").split(",");

        for (int i = 0; i < inputList.length; i++) {
//            stringBuilder.append("<p>");
            stringBuilder.append(Constants.getProperties(inputList[i]));
//            stringBuilder.append("</p>");
            stringBuilder.append(", ");
        }


        return stringBuilder.deleteCharAt(stringBuilder.length() - 2);
    }


}
