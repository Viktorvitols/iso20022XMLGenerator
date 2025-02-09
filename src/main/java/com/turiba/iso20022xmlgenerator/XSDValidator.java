package com.turiba.iso20022xmlgenerator;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.ByteArrayInputStream;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.validation.Validator;
import javax.xml.transform.dom.DOMSource;
import com.turiba.iso20022xmlgenerator.database.DBConnection;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XSDValidator {

    public boolean validateXml(String xml) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            String envelopeXSD = DBConnection.getEnvelopeXSD();
            String appHeaderXSD = DBConnection.getAppHeaderXSD();
            String documentXSD;

            if (xml.contains("pacs.008.001.08")) {
                documentXSD = DBConnection.getDocumentXSD("pacs.008.001.08");
            } else if (xml.contains("pacs.009.001.08")) {
                documentXSD = DBConnection.getDocumentXSD("pacs.009.001.08");
            } else {
                System.err.println("Unknown document format");
                return false;
            }

            InputStream inputStreamEnv = new ByteArrayInputStream(envelopeXSD.getBytes(StandardCharsets.UTF_8));
            InputStream inputStreamHead = new ByteArrayInputStream(appHeaderXSD.getBytes(StandardCharsets.UTF_8));
            InputStream inputStreamPacs = new ByteArrayInputStream(documentXSD.getBytes(StandardCharsets.UTF_8));

            Schema schemaHead = schemaFactory.newSchema(new StreamSource(inputStreamHead));
            Schema schemaPacs = schemaFactory.newSchema(new StreamSource(inputStreamPacs));
            Schema schemaEnv = schemaFactory.newSchema(new StreamSource(inputStreamEnv));

            /* TODO Fix envelope schema*/

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            Document document = factory.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));

            Validator validator = schemaEnv.newValidator();
            validator.validate(new DOMSource(document));
            validator = schemaHead.newValidator();
            validator.validate(new DOMSource(document.getElementsByTagName("head:AppHdr").item(0)));
            validator = schemaPacs.newValidator();
            validator.validate(new DOMSource(document.getElementsByTagName("Document").item(0)));

            return true;

        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMessageFormat(String message) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(message)));

            String namespace = doc.getDocumentElement().getLastChild().getPreviousSibling().getNamespaceURI();

            if ("urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08".equals(namespace)) {
                return "pacs.008.001.08";
            } else if ("urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08".equals(namespace)) {
                return "pacs.009.001.08";
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
