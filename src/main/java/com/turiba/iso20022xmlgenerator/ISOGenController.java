package com.turiba.iso20022xmlgenerator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class ISOGenController implements Initializable {

    FileGenerator fileGenerator = new FileGenerator();
    private String mesType = "";
    private String filePath = "";

    @FXML
    private ChoiceBox<String> messageSelector;
    private final String[] messageFormats = {"pacs.008.001.08", "pacs.002.001.01"};

    @FXML
    private Button genXML;

    @FXML
    private Button saveToBtn;

    @FXML
    private TextField senBankBicField;

    @FXML
    private TextField filePathField;

    @FXML
    private Label senBankLabel;

    @FXML
    protected void onGenXmlButtonClick() {
        this.filePath = filePathField.getText();
        XMLFields.setSendBankBic(senBankBicField.getText());
        String generatedXml = XMLFields.sendBankBic;
        fileGenerator.generateXmlFile(generatedXml, mesType, filePath) ;
    }

    @FXML
    public void onSaveToBtnClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Save XML file to");

        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        Stage stage = new Stage();
        File selectedDirectory = directoryChooser.showDialog(stage);
        filePathField.setText(selectedDirectory.getPath());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageSelector.getItems().addAll(messageFormats);
        messageSelector.setOnAction(this::getMessageType);
        filePathField.setOnAction(this::getFilePath);
    }

    public void getMessageType(ActionEvent event) {
        this.mesType = messageSelector.getValue();
    }

    public void getFilePath(ActionEvent event) {
        this.filePath = filePathField.getText();
    }
}
