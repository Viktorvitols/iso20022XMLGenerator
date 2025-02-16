package com.turiba.iso20022xmlgenerator.model;

public class XPaths {
    public static final String XFromField = "//AppHdr/Fr/FIId/FinInstnId/BICFI";
    public static final String XToField = "//AppHdr/To/FIId/FinInstnId/BICFI";
    public static final String XBizMsgIdrField = "//AppHdr/BizMsgIdr";
    public static final String XCreDtField = "//AppHdr/CreDt";
    public static final String XMsgIdField = "//GrpHdr/MsgId";
    public static final String XCreDtTmField = "//GrpHdr/CreDtTm";
    public static final String XInstrIdField = "//CdtTrfTxInf/PmtId/InstrId";
    public static final String XEndToEndIdField = "//CdtTrfTxInf/PmtId/EndToEndId";
    public static final String XUETRField = "//CdtTrfTxInf/PmtId/UETR";
    public static final String XIntrBkSttlmAmtField = "//CdtTrfTxInf/IntrBkSttlmAmt";
    public static final String XIntrBkSttlmCcyAttr = "//CdtTrfTxInf/IntrBkSttlmAmt/@Ccy";
    public static final String XIntrBkSttlmDtField = "//CdtTrfTxInf/IntrBkSttlmDt";
    public static final String XInstgAgtField = "//CdtTrfTxInf/InstgAgt/FinInstnId/BICFI";
    public static final String XInstdAgtField = "//CdtTrfTxInf/InstdAgt/FinInstnId/BICFI";
    public static final String XDbtrAccOthField = "//CdtTrfTxInf/DbtrAcct/Id/Othr/Id";
    public static final String XDbtrAccIbanField = "//CdtTrfTxInf/DbtrAcct/Id/IBAN";
    public static final String XDbtrNmField = "//CdtTrfTxInf/Dbtr/Nm";
    public static final String XDbtrStrtNmField = "//CdtTrfTxInf/Dbtr/PstlAdr/StrtNm";
    public static final String XDbtrTwnNmField = "//CdtTrfTxInf/Dbtr/PstlAdr/TwnNm";
    public static final String XDbtrCtryField = "//CdtTrfTxInf/Dbtr/PstlAdr/Ctry";
    public static final String XDbtrAgtField = "//CdtTrfTxInf/DbtrAgt/FinInstnId/BICFI";
    public static final String XCdtrAgtField = "//CdtTrfTxInf/CdtrAgt/FinInstnId/BICFI";
    public static final String XCdtrNmField = "//CdtTrfTxInf/Cdtr/Nm";
    public static final String XCdtrStrtNmField = "//CdtTrfTxInf/Cdtr/PstlAdr/StrtNm";
    public static final String XCdtrTwnNmField = "//CdtTrfTxInf/Cdtr/PstlAdr/TwnNm";
    public static final String XCdtrCtryField = "//CdtTrfTxInf/Cdtr/PstlAdr/Ctry";
    public static final String XCdtrAccOthField = "//CdtTrfTxInf/CdtrAcct/Id/Othr/Id";
    public static final String XCdtrAccIbanField = "//CdtTrfTxInf/CdtrAcct/Id/IBAN";
    public static final String XRmtInfField = "//CdtTrfTxInf/RmtInf/Ustrd";
    public static final String XDbtrBicField = "//CdtTrfTxInf/Dbtr/FinInstnId/BICFI";
    public static final String XCdtrBicField = "//CdtTrfTxInf/Cdtr/FinInstnId/BICFI";
}
