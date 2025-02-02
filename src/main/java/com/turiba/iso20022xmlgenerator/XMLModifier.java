package com.turiba.iso20022xmlgenerator;

import com.turiba.iso20022xmlgenerator.database.DBConnection;
import com.turiba.iso20022xmlgenerator.model.XMLFields;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class XMLModifier {

    private static final HashMap<String, String> modificationMap = new HashMap<String, String>();

    static {
        BaseFunc bf = new BaseFunc();
        UUID uuid = UUID.randomUUID();
        LocalDate CreDt = LocalDate.now();
        LocalDateTime CreDtTm = LocalDateTime.now();

        modificationMap.put(XMLFields.fromField, "/Envelope/AppHdr/Fr/FIId/FinInstnId/BICFI");
        modificationMap.put(XMLFields.toField, "/Envelope/AppHdr/To/FIId/FinInstnId/BICFI");
        modificationMap.put("BMI" + bf.generateUniqueString(), "/Envelope/AppHdr/BizMsgIdr");
        modificationMap.put(CreDt.toString(), "/Envelope/AppHdr/CreDt");
        modificationMap.put("MID"+bf.generateUniqueString(), "/Envelope/Document/FIToFICstmrCdtTrf/GrpHdr/MsgId");
        modificationMap.put(CreDtTm.toString(), "/Envelope/Document/FIToFICstmrCdtTrf/GrpHdr/CreDtTm");
        modificationMap.put("IId"+bf.generateUniqueString(), "/Envelope/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/PmtId/InstrId");
        modificationMap.put("E2E"+bf.generateUniqueString(), "/Envelope/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/PmtId/EndToEndId");
        modificationMap.put(uuid.toString(), "/Envelope/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/PmtId/UETR");
        modificationMap.put("/Envelope/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/IntrBkSttlmAmt", XMLFields.intrBkSttlmAmtField);
        modificationMap.put("/Envelope/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/IntrBkSttlmAmt/@Ccy", XMLFields.IntrBkSttlmCcyAttr);
        modificationMap.put("/Envelope/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/IntrBkSttlmDt", XMLFields.intrBkSttlmDtField);
        modificationMap.put("/Envelope/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/InstgAgt/FinInstnId/BICFI", XMLFields.instgAgtField);
        modificationMap.put("/Envelope/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/InstdAgt/FinInstnId/BICFI", XMLFields.instdAgtField);
        modificationMap.put("/Envelope/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/Dbtr/Nm", XMLFields.dbtrNmField);
        modificationMap.put("/Envelope/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/DbtrAcct/Id/Othr/Id", XMLFields.dbtrAcctField);
        modificationMap.put("/Envelope/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/DbtrAgt/FinInstnId/BICFI", XMLFields.dbtrAgtField);
        modificationMap.put("/Envelope/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/CdtrAgt/FinInstnId/BICFI", XMLFields.cdtrAgtField);
        modificationMap.put("/Envelope/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/Cdtr/Nm", XMLFields.cdtrNmField);
        modificationMap.put("/Envelope/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/CdtrAcct/Id/Othr/Id", XMLFields.cdtrAccField);
        modificationMap.put("/Envelope/Document/FIToFICstmrCdtTrf/CdtTrfTxInf/RmtInf/Ustrd", XMLFields.rmtInfField);

    }

    public String prepareXml(String templateName) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList;
        try {
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document doc = documentBuilder.parse("C:\\Test\\payment_xml\\pacs.008 1.xml");
//            nodeList = (NodeList) xPath.evaluate(modificationMap., doc, XPathConstants.NODESET);
//            nodeList.item(0).setTextContent(XMLFields.fromField);

//            if (nodeList.getLength() == 0) {
//                System.out.println("No XML elements found");
//                ;
//            } else if (nodeList.getLength() > 1) {
//                System.out.println("More then one XML element found");
//            }
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
//        } catch (XPathExpressionException e) {
//            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "";
    }
}

