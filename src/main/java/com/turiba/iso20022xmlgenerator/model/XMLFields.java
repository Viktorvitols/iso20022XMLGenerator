package com.turiba.iso20022xmlgenerator.model;

public class XMLFields {
    public static String fromField;
    public static String toField;
    public static String endToEndIdField;
    public static String intrBkSttlmAmtField;
    public static String intrBkSttlmCcyAttr;
    public static String intrBkSttlmDtField;
    public static String instdAgtField;
    public static String instgAgtField;
    public static String dbtrAccField;
    public static String dbtrAccIbanField;
    public static String dbtrNmField;
    public static String dbtrStrtNmField;
    public static String dbtrTwnNmField;
    public static String dbtrCtryField;
    public static String dbtrAgtField;
    public static String cdtrAgtField;
    public static String cdtrNmField;
    public static String cdtrStrtNmField;
    public static String cdtrTwnNmField;
    public static String cdtrCtryField;
    public static String cdtrAccField;
    public static String cdtrAccIbanField;
    public static String rmtInfField;
    public static String cdtrBicField;
    public static String dbtrBicField;

    public static void setFromField(String fromField) {
        XMLFields.fromField = fromField.toUpperCase();
    }

    public static void setToField(String toField) {
        XMLFields.toField = toField.toUpperCase();
    }

    public static void setEndToEndIdField(String endToEndIdField) {
        XMLFields.endToEndIdField = endToEndIdField;
    }

    public static void setIntrBkSttlmAmtField(String intrBkSttlmAmtField) {
        XMLFields.intrBkSttlmAmtField = intrBkSttlmAmtField;
    }

    public static void setIntrBkSttlmCcyAttr(String intrBkSttlmCcyAttr) {
        XMLFields.intrBkSttlmCcyAttr = intrBkSttlmCcyAttr.toUpperCase();
    }

    public static void setIntrBkSttlmDtField(String intrBkSttlmDtField) {
        XMLFields.intrBkSttlmDtField = intrBkSttlmDtField;
    }

    public static void setInstdAgtField(String instdAgtField) {
        XMLFields.instdAgtField = instdAgtField.toUpperCase();
    }

    public static void setInstgAgtField(String instgAgtField) {
        XMLFields.instgAgtField = instgAgtField.toUpperCase();
    }

    public static void setDbtrAccField(String dbtrAccField) {
        XMLFields.dbtrAccField = dbtrAccField.toUpperCase();
    }

    public static void setDbtrNmField(String dbtrNmField) {
        XMLFields.dbtrNmField = dbtrNmField.toUpperCase();
    }

    public static void setDbtrStrtNmField(String dbtrStrtNmField) {
        XMLFields.dbtrStrtNmField = dbtrStrtNmField;
    }

    public static void setDbtrTwnNmField(String dbtrTwnNmField) {
        XMLFields.dbtrTwnNmField = dbtrTwnNmField;
    }

    public static void setDbtrCtryField(String dbtrCtryField) {
        XMLFields.dbtrCtryField = dbtrCtryField.toUpperCase();
    }

    public static void setDbtrAgtField(String dbtrAgtField) {
        XMLFields.dbtrAgtField = dbtrAgtField.toUpperCase();
    }

    public static void setCdtrAgtField(String cdtrAgtField) {
        XMLFields.cdtrAgtField = cdtrAgtField.toUpperCase();
    }

    public static void setCdtrNmField(String cdtrNmField) {
        XMLFields.cdtrNmField = cdtrNmField;
    }

    public static void setCdtrStrtNmField(String cdtrStrtNmField) {
        XMLFields.cdtrStrtNmField = cdtrStrtNmField;
    }

    public static void setCdtrTwnNmField(String cdtrTwnNmField) {
        XMLFields.cdtrTwnNmField = cdtrTwnNmField;
    }

    public static void setCdtrCtryField(String cdtrCtryField) {
        XMLFields.cdtrCtryField = cdtrCtryField.toUpperCase();
    }

    public static void setCdtrAccField(String cdtrAccField) {
        XMLFields.cdtrAccField = cdtrAccField.toUpperCase();
    }

    public static void setDbtrAccIbanField(String dbtrAccIbanField) {
        XMLFields.dbtrAccIbanField = dbtrAccIbanField;
    }

    public static void setCdtrAccIbanField(String cdtrAccIbanField) {
        XMLFields.cdtrAccIbanField = cdtrAccIbanField;
    }

    public static void setRmtInfField(String details) {
        XMLFields.rmtInfField = details.trim();
    }

    public static void setCdtrBicField(String cdtrBicField) {
        XMLFields.cdtrBicField = cdtrBicField.toUpperCase();
    }

    public static void setDbtrBicField(String dbtrBicField) {
        XMLFields.dbtrBicField = dbtrBicField.toUpperCase();
    }

}
