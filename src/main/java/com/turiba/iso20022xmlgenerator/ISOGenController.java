package com.turiba.iso20022xmlgenerator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class ISOGenController implements Initializable {

    FileGenerator fileGenerator = new FileGenerator();
    private String generatedXml = "";
    private String mesType = "";

    @FXML
    private ChoiceBox<String> messageSelector;
    private String[] messageFormats = {"pacs.008.001.08", "pacs.002.001.01"};

    @FXML
    private Button genXML;

    @FXML
    private TextField senBankBicField;


    @FXML
    private Label senBankLabel;

    @FXML
    protected void onGenXmlButtonClick() {
        XMLFields.setSendBankBic(senBankBicField.getText());

        generatedXml = XMLFields.sendBankBic;
        fileGenerator.generateXmlFile(generatedXml, mesType) ;

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        messageSelector.getItems().addAll(messageFormats);
        messageSelector.setOnAction(this::getMessageType);
    }

    public void getMessageType(ActionEvent event) {
        this.mesType = messageSelector.getValue();
    }

}
