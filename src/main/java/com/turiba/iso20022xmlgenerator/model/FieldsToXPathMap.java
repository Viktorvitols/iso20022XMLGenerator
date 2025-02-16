package com.turiba.iso20022xmlgenerator.model;

import com.turiba.iso20022xmlgenerator.BaseFunc;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.UUID;

public class FieldsToXPathMap {

    public HashMap<String, String> modificationMap = new HashMap<>();
    public FieldsToXPathMap(){
        BaseFunc bf = new BaseFunc();

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
        modificationMap.put(XPaths.XDbtrAccOthField, XMLFields.dbtrAccField);
        modificationMap.put(XPaths.XDbtrAccIbanField, XMLFields.dbtrAccIbanField);
        modificationMap.put(XPaths.XDbtrNmField, XMLFields.dbtrNmField);
        modificationMap.put(XPaths.XDbtrStrtNmField, XMLFields.dbtrStrtNmField);
        modificationMap.put(XPaths.XDbtrTwnNmField, XMLFields.dbtrTwnNmField);
        modificationMap.put(XPaths.XDbtrCtryField, XMLFields.dbtrCtryField);
        modificationMap.put(XPaths.XDbtrAgtField, XMLFields.dbtrAgtField);
        modificationMap.put(XPaths.XCdtrAgtField, XMLFields.cdtrAgtField);
        modificationMap.put(XPaths.XCdtrNmField, XMLFields.cdtrNmField);
        modificationMap.put(XPaths.XCdtrStrtNmField, XMLFields.cdtrStrtNmField);
        modificationMap.put(XPaths.XCdtrTwnNmField, XMLFields.cdtrTwnNmField);
        modificationMap.put(XPaths.XCdtrCtryField, XMLFields.cdtrCtryField);
        modificationMap.put(XPaths.XCdtrAccOthField, XMLFields.cdtrAccField);
        modificationMap.put(XPaths.XCdtrAccIbanField, XMLFields.cdtrAccIbanField);
        modificationMap.put(XPaths.XRmtInfField, XMLFields.rmtInfField);
        modificationMap.put(XPaths.XDbtrBicField, XMLFields.dbtrBicField);
        modificationMap.put(XPaths.XCdtrBicField, XMLFields.cdtrBicField);
    }
}
