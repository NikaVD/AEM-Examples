//package com.traineeproject.core.services;
//import com.adobe.aemfd.docmanager.Document;
//import com.adobe.fd.assembler.client.*;
//import com.adobe.fd.assembler.service.AssemblerService;
//import org.osgi.service.component.annotations.Component;
//import org.osgi.service.component.annotations.Reference;
//import org.w3c.dom.Element;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.TransformerConfigurationException;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.TransformerFactoryConfigurationError;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Objects;
//
//@Component(service = ConverterService.class, immediate = true)
//public class ConverterService {
//
//    @Reference
//    private AssemblerService assemblerService;
//
//    public File convertToPDFA(File file) throws ConversionException, IOException, ValidationException {
//        Path filePath = Files.createTempFile("pdfa", ".pdf");
//        File pdfaFile = filePath.toFile();
//        com.adobe.aemfd.docmanager.Document document = new com.adobe.aemfd.docmanager.Document(file);
//
//
//        PDFAConversionOptionSpec optionSpec = new PDFAConversionOptionSpec();
//        optionSpec.setCompliance(PDFAConversionOptionSpec.Compliance.PDFA_3B);
//
//        PDFAConversionResult result = assemblerService.toPDFA(document, optionSpec);
//
//        com.adobe.aemfd.docmanager.Document resultDocument = result.getPDFADocument();
//        PDFAValidationResult validationResult = validate(resultDocument);
//        boolean isPdfa = validationResult.isPDFA();
//        Document log = validationResult.getValidationLog();
//        resultDocument.copyToFile(pdfaFile);
//        return pdfaFile;
//    }
//
//    private PDFAValidationResult validate(Document document) throws ValidationException {
//        PDFAValidationOptionSpec optionSpec = new PDFAValidationOptionSpec();
//        optionSpec.setCompliance(PDFAConversionOptionSpec.Compliance.PDFA_3B);
//        return assemblerService.isPDFA(document, optionSpec);
//    }
//
//    public File assembleDocuments(Map<String, Object> mapOfDocuments) throws IOException {
//        Path filePath = Files.createTempFile("embed", ".pdf");
//        File file = filePath.toFile();
//        AssemblerOptionSpec aoSpec = new AssemblerOptionSpec();
//        aoSpec.setFailOnError(true);
//        AssemblerResult ar = null;
//
//        try {
//            ar = this.assemblerService.invoke(createDDXFromMapOfDocuments(mapOfDocuments), mapOfDocuments, aoSpec);
//        } catch (OperationException var6) {
//            var6.printStackTrace();
//        }
//        Document doc = (Document) Objects.requireNonNull(ar).getDocuments().get("GeneratedDocument.pdf");
//        doc.copyToFile(file);
//        return file;
//    }
//
//    public Document createDDXFromMapOfDocuments(Map<String, Object> mapOfDocuments) {
//        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
//        org.w3c.dom.Document ddx = null;
//
//        try {
//            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
//            ddx = docBuilder.newDocument();
//            Element rootElement = ddx.createElementNS("http://ns.adobe.com/DDX/1.0/", "DDX");
//            ddx.appendChild(rootElement);
//            Element pdfResult = ddx.createElement("PDF");
//            pdfResult.setAttribute("result", "GeneratedDocument.pdf");
//            rootElement.appendChild(pdfResult);
//            Iterator var7 = mapOfDocuments.keySet().iterator();
//
//            while(var7.hasNext()) {
//                String key = (String)var7.next();
//                if (key.equals("faData.xml")) {
//                    Element fileAttachmentsElement = ddx.createElement("FileAttachments");
//                    fileAttachmentsElement.setAttribute("source", "faData.xml");
//
//                    Element file = ddx.createElement("File");
//                    file.setAttribute("filename", "data.xml");
//                    file.setAttribute("mimetype", "application/xml");
//
//                    Element filenameEncoding = ddx.createElement("FilenameEncoding");
//                    filenameEncoding.setAttribute("encoding", "UTF-8");
//
//                    Element description = ddx.createElement("Description");
//                    description.setNodeValue("What this file does");
//
//                    fileAttachmentsElement.appendChild(file);
//                    fileAttachmentsElement.appendChild(filenameEncoding);
//                    fileAttachmentsElement.appendChild(description);
//
//                    pdfResult.appendChild(fileAttachmentsElement);
//
//                } else {
//                    Element pdfSourceElement = ddx.createElement("PDF");
//                    pdfSourceElement.setAttribute("source", key);
//                    pdfResult.appendChild(pdfSourceElement);
//                }
//            }
//
//
//        } catch (ParserConfigurationException var10) {
//            var10.printStackTrace();
//        }
//
//        return this.orgw3cDocumentToAEMFDDocument(ddx);
//    }
//
//    public Document orgw3cDocumentToAEMFDDocument(org.w3c.dom.Document xmlDocument) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        DOMSource source = new DOMSource(xmlDocument);
//        StreamResult outputTarget = new StreamResult(outputStream);
//
//        try {
//            TransformerFactory.newInstance().newTransformer().transform(source, outputTarget);
//        } catch (TransformerConfigurationException var7) {
//            var7.printStackTrace();
//        } catch (TransformerException var8) {
//            var8.printStackTrace();
//        } catch (TransformerFactoryConfigurationError var9) {
//            var9.printStackTrace();
//        }
//
//        InputStream is1 = new ByteArrayInputStream(outputStream.toByteArray());
//        Document xmlAEMFDDocument = new Document(is1);
//        return xmlAEMFDDocument;
//    }
//
//}
