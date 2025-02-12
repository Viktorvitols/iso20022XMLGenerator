package com.turiba.iso20022xmlgenerator;

import com.turiba.iso20022xmlgenerator.database.DBConnection;
import com.turiba.iso20022xmlgenerator.model.FieldsToXPathMap;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.transform.*;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

public class XMLModifier {

    public String prepareXml(String templateName) {

        String template = DBConnection.getXmlTemplateByName(templateName);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        XPath xPath = XPathFactory.newInstance().newXPath();
        FieldsToXPathMap map = new FieldsToXPathMap();
        try {
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(template));
            Document doc = documentBuilder.parse(inputSource);

            for (Map.Entry<String, String> entry : map.modificationMap.entrySet()) {
                String xpathExpression = entry.getKey();
                String newValue = entry.getValue();

                if (newValue == null || newValue.isEmpty()) {
                    continue;
                }
                NodeList nodeList = (NodeList) xPath.evaluate(xpathExpression, doc, XPathConstants.NODESET);
                if (nodeList.getLength() > 0) {

                    for (int i = 0; i < nodeList.getLength(); i++) {
                        nodeList.item(i).setTextContent(newValue);
                    }
                }
            }
            return convertDocumentToString(doc);

        } catch (ParserConfigurationException | IOException | SAXException |
                 XPathExpressionException | TransformerException e) {
            throw new RuntimeException("Error processing XML", e);
        }
    }

    private static String convertDocumentToString(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return writer.toString();
    }
}
