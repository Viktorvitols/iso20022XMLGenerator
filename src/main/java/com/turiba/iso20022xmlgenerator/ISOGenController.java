package com.turiba.iso20022xmlgenerator;

import com.turiba.iso20022xmlgenerator.database.DBConnection;
import com.turiba.iso20022xmlgenerator.model.XMLFields;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class ISOGenController implements Initializable {

    FileGenerator fileGenerator = new FileGenerator();
    XMLModifier xmlModifier = new XMLModifier();
    private String templateName;
    private String filePath = "";

    @FXML
    private ChoiceBox<String> messageSelector;
    private List<String> messageFormats = DBConnection.getTemplateNames();

    @FXML
    private TextField fromField;

    @FXML
    private TextField toField;

    @FXML
    private TextField IntrBkSttlmCcyAttr;

    @FXML
    private TextField cdtrAccField;

    @FXML
    private TextField cdtrAgtField;

    @FXML
    private TextField cdtrCtryField;

    @FXML
    private TextField cdtrNmField;

    @FXML
    private TextField cdtrStrField;

    @FXML
    private TextField cdtrTownField;

    @FXML
    private TextField dbtrAcctField;

    @FXML
    private TextField dbtrAgtField;

    @FXML
    private TextField dbtrCtryField;

    @FXML
    private TextField dbtrNmField;

    @FXML
    private TextField dbtrStrField;

    @FXML
    private TextField dbtrTownField;

    @FXML
    private TextField e2eidField;

    @FXML
    private TextField filePathField;

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
    private Button genXML;

    @FXML
    private Button newTemplateBtn;

    @FXML
    private Button saveToBtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageSelector.getItems().addAll(messageFormats);
        messageSelector.setOnAction(this::getTemplateName);
        messageSelector.setValue(messageFormats.get(0));

        filePathField.setOnAction(this::getFilePath);
    }


    public void getTemplateName(ActionEvent event) {
        this.templateName = messageSelector.getValue();
    }

    public void getFilePath(ActionEvent event) {
        this.filePath = filePathField.getText();
    }

    private void setupXmlObject() {
        XMLFields.setFromField(fromField.getText());
        XMLFields.setToField(toField.getText());
        XMLFields.setEndToEndIdField(e2eidField.getText());
        XMLFields.setIntrBkSttlmAmtField(intrBkSttlmAmtField.getText());
        XMLFields.setIntrBkSttlmCcyAttr(IntrBkSttlmCcyAttr.getText());
        XMLFields.setIntrBkSttlmDtField(intrBkSttlmDtField.getText());
        XMLFields.setInstdAgtField(instdAgtField.getText());
        XMLFields.setInstgAgtField(instgAgtField.getText());
        XMLFields.setDbtrAgtField(dbtrAgtField.getText());
        XMLFields.setCdtrAgtField(cdtrAgtField.getText());
        XMLFields.setDbtrAcctField(dbtrAcctField.getText());
        XMLFields.setDbtrNmField(dbtrNmField.getText());
        XMLFields.setCdtrAccField(cdtrAccField.getText());
        XMLFields.setCdtrNmField(cdtrNmField.getText());
        XMLFields.setDbtrStrtNmField(dbtrStrField.getText());
        XMLFields.setCdtrStrtNmField(cdtrStrField.getText());
        XMLFields.setDbtrCtryField(dbtrCtryField.getText());
        XMLFields.setDbtrTwnNmField(dbtrTownField.getText());
        XMLFields.setCdtrCtryField(cdtrCtryField.getText());
        XMLFields.setCdtrTwnNmField(cdtrTownField.getText());
        XMLFields.setRmtInfField(rmtInfField.getText());
    }

    /* TODO fill in fields on template change */
    public void fillInFields(String templateName) {

    }


    @FXML
    protected void onGenXmlButtonClick() {
        this.filePath = filePathField.getText();
        setupXmlObject();
        String generatedXml = xmlModifier.prepareXml(templateName);

        /* TODO validate xml */

        fileGenerator.generateXmlFile(generatedXml, templateName, filePath);
    }

    @FXML
    public void onSaveToBtnClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Save XML file to");

        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        Stage stage = new Stage();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            filePathField.setText(selectedDirectory.getPath());
        }
    }

    @FXML
    void onNewTemplateBtnClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select new template file.");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stage);

        /* TODO Validate xml */


        if (selectedFile != null) {
            try {
                String fileName = selectedFile.getName();
                String xml = new String(Files.readAllBytes(Paths.get(selectedFile.getAbsolutePath())));

                if (DBConnection.getXmlTemplateByName(fileName) == null) {
                    DBConnection.saveNewTemplate(fileName, xml);
                    System.out.println("New template added");
                    updateTemplateList();
                    System.out.println("List updated");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateTemplateList() {
        messageSelector.getItems().clear();
        List<String> updatedMessageFormats = DBConnection.getTemplateNames();
        messageSelector.getItems().addAll(updatedMessageFormats);
    }


}
