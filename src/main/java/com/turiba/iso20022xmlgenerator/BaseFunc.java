package com.turiba.iso20022xmlgenerator;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BaseFunc {

    public String generateUniqueString() {
        LocalTime time = LocalTime.now();
        return time.format(DateTimeFormatter.ofPattern("Hmsn")).substring(0,12);
    }

    public String today(){
        LocalDate date = LocalDate.now();
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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

    public void showCustomDialogMessage(String title, String errorText, Control control) {
        Stage primaryStage = (Stage) control.getScene().getWindow();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setContentText(errorText);
        dialog.initOwner(primaryStage);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    }
}
