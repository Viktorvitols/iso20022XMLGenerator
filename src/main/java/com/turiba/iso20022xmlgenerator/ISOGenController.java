package com.turiba.iso20022xmlgenerator;

import com.turiba.iso20022xmlgenerator.database.DBConnection;
import com.turiba.iso20022xmlgenerator.model.XMLFields;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ISOGenController implements Initializable {

    FileGenerator fileGenerator = new FileGenerator();
    private String mesType = "";
    private String filePath = "";

    @FXML
    private ChoiceBox<String> messageSelector;
    private final List<String> messageFormats = DBConnection.getMessageTypes();

    @FXML
    private TextField IntrBkSttlmCcyAttr;

    @FXML
    private TextField cdtrAccField;

    @FXML
    private TextField cdtrAgtField;

    @FXML
    private TextField cdtrNmField;

    @FXML
    private TextField dbtrAcctField;

    @FXML
    private TextField dbtrAgtField;

    @FXML
    private TextField dbtrNmField;

    @FXML
    private TextField filePathField;

    @FXML
    private TextField fromField;

    @FXML
    private Button genXML;

    @FXML
    private TextField instdAgtField;

    @FXML
    private TextField instgAgtField;

    @FXML
    private TextField intrBkSttlmAmtField;

    @FXML
    private TextField intrBkSttlmDtField;

    @FXML
    private TextArea rmtInfField;

    @FXML
    private Button saveToBtn;

    @FXML
    private Label senBankLabel;

    @FXML
    private Label senBankLabel1;

    @FXML
    private Label senBankLabel2;

    @FXML
    private TextField toField;

    @FXML
    private TextFlow xmlPreviewArea;

    @FXML
    protected void onGenXmlButtonClick() {
        this.filePath = filePathField.getText();
        setupXmlObject();
        String generatedXml = prepareXml(DBConnection.
                getXmlTemplate(mesType));

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

    private void setupXmlObject(){
        XMLFields.setFromField(fromField.getText());
        XMLFields.setToField(toField.getText());
        XMLFields.setIntrBkSttlmAmtField(intrBkSttlmAmtField.getText());
        XMLFields.setIntrBkSttlmCcyAttr(IntrBkSttlmCcyAttr.getText());
        XMLFields.setIntrBkSttlmDtField(intrBkSttlmDtField.getText());
        XMLFields.setInstdAgtField(instdAgtField.getText());
        XMLFields.setInstgAgtField(instgAgtField.getText());
        XMLFields.setDbtrAcctField(dbtrAcctField.getText());
        XMLFields.setDbtrNmField(dbtrNmField.getText());
        XMLFields.setDbtrAgtField(dbtrAgtField.getText());
        XMLFields.setCdtrAgtField(cdtrAgtField.getText());
        XMLFields.setCdtrNmField(cdtrNmField.getText());
        XMLFields.setCdtrAccField(cdtrAccField.getText());
        XMLFields.setRmtInfField(rmtInfField.getText());
    }

    private String prepareXml(String template) {

        return "";
    }
}
