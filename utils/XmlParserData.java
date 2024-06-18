package com.traineeproject.core.utils;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class XmlParserData {
    public static String parseXmpDataDoc(String file) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        Document document;
        document = loadXml(file);
        unwrapXml(document,"data");
        return writeXml(document);
    }

    public static Document parseXmpData(String file) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        Document document;
        document = loadXml(file);
        return unwrapXml(document,"data");

    }



    static Document loadXml(String resource) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        InputStream xmlIS = IOUtils.toInputStream(resource, StandardCharsets.UTF_8);
        Document document = dbf.newDocumentBuilder().parse(xmlIS);
        document.getDocumentElement().normalize();
        return document;
    }

    static Document unwrapXml(Document doc, String tagName) throws ParserConfigurationException {
        String root = doc.getDocumentElement().getNodeName();
        Node node = doc.getElementsByTagName(tagName).item(0);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document result = dBuilder.newDocument();
        Node importNode = result.importNode(node, true);
        result.appendChild(importNode);
        return result;
    }

    static String writeXml (Document doc) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();

        StringWriter out = new StringWriter();
        DOMSource domSource = new DOMSource(doc);
        StreamResult result = new StreamResult(out);

        t.transform(domSource, result);
        return out.toString();
    }


}
