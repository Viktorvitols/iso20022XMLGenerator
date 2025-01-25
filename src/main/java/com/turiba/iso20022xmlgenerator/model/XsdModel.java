package com.turiba.iso20022xmlgenerator.model;

public class XsdModel {
    private Integer id;
    private String for_message;
    private String envelope;
    private String header;
    private String document;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFor_message() {
        return for_message;
    }

    public void setFor_message(String for_message) {
        this.for_message = for_message;
    }

    public String getEnvelope() {
        return envelope;
    }

    public void setEnvelope(String envelope) {
        this.envelope = envelope;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}