package com.traineeproject.core.utils;

import com.traineeproject.core.servlets.GeneratePDFAService;
import org.apache.log4j.FileAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class EmbeddedXmp extends FileAppender {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratePDFAService.class);


    public static void embeddedXmp(String src, String dest) throws IOException {
        LOGGER.info("================AddEmbedded Start==============");
//        try (PDDocument document = Loader.loadPDF(new File(src))) {
//            if (document.isEncrypted())
//                 {
//                     LOGGER.info( "Error: Cannot add metadata to encrypted document." );
//                System.exit( 1 );
//                }

//        }

//        PDDocumentCatalog catalog = document.getDocumentCatalog();
//        PDDocumentInformation info = document.getDocumentInformation();
//
//        XMPMetadata metadata = XMPMetadata.createXMPMetadata();
//
//        AdobePDFSchema pdfSchema = metadata.createAndAddAdobePDFSchema();
//        pdfSchema.setKeywords( info.getKeywords() );
//        pdfSchema.setProducer( info.getProducer() );
//
//        XMPBasicSchema basicSchema = metadata.createAndAddXMPBasicSchema();
//        basicSchema.setModifyDate( info.getModificationDate() );
//        basicSchema.setCreateDate( info.getCreationDate() );
//        basicSchema.setCreatorTool( info.getCreator() );
//        basicSchema.setMetadataDate( new GregorianCalendar() );
//
//        DublinCoreSchema dcSchema = metadata.createAndAddDublinCoreSchema();
//        dcSchema.setTitle( info.getTitle() );
//        dcSchema.addCreator( "PDFBox" );
//        dcSchema.setDescription( info.getSubject() );
//
//        PDMetadata metadataStream = new PDMetadata(document);
//        catalog.setMetadata( metadataStream );
//
//        XmpSerializer serializer = new XmpSerializer();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        serializer.serialize(metadata, baos, false);
//        metadataStream.importXMPMetadata( baos.toByteArray() );
//
//        document.save(new File(dest));

    }
}
