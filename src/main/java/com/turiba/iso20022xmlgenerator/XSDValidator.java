package com.turiba.iso20022xmlgenerator;

import com.turiba.iso20022xmlgenerator.database.DBConnection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.ByteArrayInputStream;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Validator;
import javax.xml.transform.dom.DOMSource;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;

public class XSDValidator {

    public boolean validateXml(String xml) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            String appHeaderXSD = DBConnection.getXsdForMesType("head.001.001.02");
            String envelopeXSD;
            String documentXSD;

            if (xml.contains("pacs.008.001.08")) {
                documentXSD = DBConnection.getXsdForMesType("pacs.008.001.08");
                envelopeXSD = DBConnection.getXsdForMesType("envelope008");
            } else if (xml.contains("pacs.009.001.08")) {
                documentXSD = DBConnection.getXsdForMesType("pacs.009.001.08");
                envelopeXSD = DBConnection.getXsdForMesType("envelope009");

            } else {
                System.err.println("Unknown document format");
                return false;
            }

            if (appHeaderXSD == null || envelopeXSD == null || documentXSD == null) {
                System.out.println("No XSD schema");
                return false;
            }
            Schema schema = schemaFactory.newSchema(new Source[]{
                    new StreamSource(new StringReader(appHeaderXSD)),
                    new StreamSource(new StringReader(documentXSD)),
                    new StreamSource(new StringReader(envelopeXSD))
            });

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            Document document = factory.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(document));

            System.out.println("XML is valid!");
            return true;

        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
            return false;
        }
    }
}
