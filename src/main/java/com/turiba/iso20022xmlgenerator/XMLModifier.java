package com.turiba.iso20022xmlgenerator;

import com.turiba.iso20022xmlgenerator.database.DBConnection;
import com.turiba.iso20022xmlgenerator.model.XMLFields;
import com.turiba.iso20022xmlgenerator.model.XPaths;
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
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class XMLModifier {

    private HashMap<String, String> modificationMap = new HashMap<String, String>();

    BaseFunc bf = new BaseFunc();

    public String prepareXml(String templateName) {
        UUID uuid = UUID.randomUUID();
        OffsetDateTime creDt = OffsetDateTime.now();
        OffsetDateTime creDtTm = OffsetDateTime.now();

        String bizMsgIdr = "BMI" + bf.generateUniqueString();
        String msgId = "MSG" + bf.generateUniqueString();
        String instrId = "IID" + bf.generateUniqueString();

        modificationMap.put(XPaths.XFromField, XMLFields.fromField);
        modificationMap.put(XPaths.XToField, XMLFields.toField);
        modificationMap.put(XPaths.XBizMsgIdrField, bizMsgIdr);
        modificationMap.put(XPaths.XCreDtField, creDt.toString());
        modificationMap.put(XPaths.XMsgIdField, msgId);
        modificationMap.put(XPaths.XCreDtTmField, creDtTm.toString());
        modificationMap.put(XPaths.XInstrIdField, instrId);
        modificationMap.put(XPaths.XEndToEndIdField, XMLFields.endToEndIdField);
        modificationMap.put(XPaths.XIntrBkSttlmAmtField, XMLFields.intrBkSttlmAmtField);
        modificationMap.put(XPaths.XIntrBkSttlmCcyAttr, XMLFields.intrBkSttlmCcyAttr);
        modificationMap.put(XPaths.XIntrBkSttlmDtField, XMLFields.intrBkSttlmDtField);
        modificationMap.put(XPaths.XUETRField, uuid.toString());
        modificationMap.put(XPaths.XInstgAgtField, XMLFields.instgAgtField);
        modificationMap.put(XPaths.XInstdAgtField, XMLFields.instdAgtField);
        modificationMap.put(XPaths.XDbtrAcctField, XMLFields.dbtrAcctField);
        modificationMap.put(XPaths.XDbtrNmField, XMLFields.cdtrNmField);
        modificationMap.put(XPaths.XDbtrStrtNmField, XMLFields.dbtrStrtNmField);
        modificationMap.put(XPaths.XDbtrTwnNmField, XMLFields.dbtrTwnNmField);
        modificationMap.put(XPaths.XDbtrCtryField, XMLFields.dbtrCtryField);
        modificationMap.put(XPaths.XDbtrAgtField, XMLFields.dbtrAgtField);
        modificationMap.put(XPaths.XCdtrAgtField, XMLFields.cdtrAgtField);
        modificationMap.put(XPaths.XCdtrNmField, XMLFields.cdtrNmField);
        modificationMap.put(XPaths.XCdtrStrtNmField, XMLFields.cdtrStrtNmField);
        modificationMap.put(XPaths.XCdtrTwnNmField, XMLFields.cdtrTwnNmField);
        modificationMap.put(XPaths.XCdtrCtryField, XMLFields.cdtrCtryField);
        modificationMap.put(XPaths.XCdtrAccField, XMLFields.cdtrAccField);
        modificationMap.put(XPaths.XRmtInfField, XMLFields.rmtInfField);


        String template = DBConnection.getXmlTemplateByName(templateName);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        XPath xPath = XPathFactory.newInstance().newXPath();

        try {
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(template));
            Document doc = documentBuilder.parse(inputSource);

            for (Map.Entry<String, String> entry : modificationMap.entrySet()) {
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

