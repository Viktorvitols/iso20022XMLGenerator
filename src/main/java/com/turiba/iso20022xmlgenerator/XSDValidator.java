package com.turiba.iso20022xmlgenerator;

import java.util.List;
import javax.xml.validation.SchemaFactory;
import java.util.ArrayList;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import javax.xml.validation.Schema;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XSDValidator {

    public static boolean validateXml(String xml, List<String> xsdSchemas) {
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            List<StreamSource> sources = new ArrayList<>();
            for (String xsd : xsdSchemas) {
                sources.add(new StreamSource(new StringReader(xsd)));
            }

            Schema schema = sf.newSchema(sources.toArray(new StreamSource[0]));

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setSchema(schema);

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(
                    new InputSource(
                            new StringReader(xml))
            );

            System.out.println("XML is valid!");
            return true;
        } catch (SAXException e) {
            System.err.println("XML validation error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
