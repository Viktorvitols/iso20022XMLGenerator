package com.turiba.iso20022xmlgenerator;

import com.turiba.iso20022xmlgenerator.database.DBConnection;
import com.turiba.iso20022xmlgenerator.model.ChargeBearer;
import com.turiba.iso20022xmlgenerator.model.XMLFields;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ISOGenController implements Initializable {

    FileGenerator fileGenerator = new FileGenerator();
    XMLModifier xmlModifier = new XMLModifier();
    private String templateName = "";
    private String filePath = "";
    private Enum chargeBearer;

    private List<Enum> getChrgBearersList() {
        List<Enum> chargeBearers = new ArrayList<>();
        for (ChargeBearer chrgBr : ChargeBearer.values()) {
            chargeBearers.add(chrgBr);
        }
        return chargeBearers;
    }

    @FXML
    private ChoiceBox<String> messageSelector;
    private final List<String> messageFormats = DBConnection.getTemplateNames();

    @FXML
    private ChoiceBox<Enum> chrgBrSelector;
    private final List<Enum> chrgBearersList = getChrgBearersList();

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
    private TextField chrgAgtField;

    @FXML
    private TextField chrgAmtField;

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
    private Button saveTemplateBtn;

    @FXML
    private Button saveToBtn;

    @FXML
    private TextField toField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageSelector.getItems().addAll(messageFormats);
        messageSelector.setOnAction(this::getTemplateName);
        chrgBrSelector.getItems().addAll(chrgBearersList);
        chrgBrSelector.setOnAction((this::getChargeBearer));

        filePathField.setOnAction(this::getFilePath);
    }


    @FXML
    protected void onGenXmlButtonClick() {
        this.filePath = filePathField.getText();
        setupXmlObject();
        String generatedXml = xmlModifier.prepareXml(DBConnection.
                getXmlTemplateByName(templateName));

        //validate xml

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
    void onSaveTemplateBtnClick(ActionEvent event) {
        //Save template to the DB

    }

    public void getTemplateName(ActionEvent event) {
        this.templateName = messageSelector.getValue();
    }

    public void getChargeBearer(ActionEvent event) {
        this.chargeBearer = chrgBrSelector.getValue();
        switch (chargeBearer.toString()) {
            case "DEBT":
                chrgAgtField.setDisable(true);
                chrgAmtField.setDisable(true);
                break;
            case "CRED":
                chrgAgtField.setDisable(false);
                chrgAmtField.setDisable(false);
                break;
            case "SHAR":
                chrgAgtField.setDisable(false);
                chrgAmtField.setDisable(false);
                break;
        }
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
        XMLFields.setChrgBrField(String.valueOf(chrgBrSelector.getValue()));
        XMLFields.setChrgAgtField(chrgAgtField.getText());
        XMLFields.setChrgAmtField(chrgAmtField.getText());
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

    //fill in fields on template change
    public void fillInFields(String templateName) {

    }

}
