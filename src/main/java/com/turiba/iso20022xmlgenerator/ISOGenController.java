package com.turiba.iso20022xmlgenerator;

import com.turiba.iso20022xmlgenerator.database.DBConnection;
import com.turiba.iso20022xmlgenerator.model.XMLFields;
import com.turiba.iso20022xmlgenerator.model.XPaths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ISOGenController implements Initializable {

    FileGenerator fileGenerator = new FileGenerator();
    XMLModifier xmlModifier = new XMLModifier();
    private String templateName;
    private String filePath = "";

    @FXML
    private ChoiceBox<String> messageSelector;
    private List<String> templateList = new ArrayList<>();

    @FXML
    private TextField fromField;

    @FXML
    private TextField toField;

    @FXML
    private TextField intrBkSttlmCcyAttr;

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
    private TextArea detailsField;

    @FXML
    private TextField dbtrBicField;

    @FXML
    private TextField cdtrBicField;

    @FXML
    private RadioButton pacs008Radio;

    @FXML
    private RadioButton pacs009Radio;

    @FXML
    private ToggleGroup pacsFormat;

    @FXML
    private Button genXML;

    @FXML
    private Button newTemplateBtn;

    @FXML
    private Button saveToBtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageSelector.setOnAction(this::getTemplateName);
        messageSelector.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        fillInFields(newValue);
                    }
                });

        filePathField.setOnAction(this::getFilePath);

        prepareGuiFields(new ActionEvent(pacsFormat.getSelectedToggle(), null));

    }

    public void prepareGuiFields(ActionEvent event) {
        if (pacs008Radio.isSelected()) {
            dbtrBicField.setDisable(true);
            cdtrBicField.setDisable(true);
            dbtrNmField.setDisable(false);
            cdtrNmField.setDisable(false);
            dbtrAcctField.setDisable(false);
            cdtrAccField.setDisable(false);
            dbtrStrField.setDisable(false);
            cdtrStrField.setDisable(false);
            dbtrCtryField.setDisable(false);
            cdtrCtryField.setDisable(false);
            dbtrTownField.setDisable(false);
            cdtrTownField.setDisable(false);
            refreshListOfTemplates();
        } else if (pacs009Radio.isSelected()) {
            dbtrBicField.setDisable(false);
            cdtrBicField.setDisable(false);
            dbtrNmField.setDisable(true);
            cdtrNmField.setDisable(true);
            dbtrAcctField.setDisable(true);
            cdtrAccField.setDisable(true);
            dbtrStrField.setDisable(true);
            cdtrStrField.setDisable(true);
            dbtrCtryField.setDisable(true);
            cdtrCtryField.setDisable(true);
            dbtrTownField.setDisable(true);
            cdtrTownField.setDisable(true);
            refreshListOfTemplates();
        }
    }

    private void refreshListOfTemplates() {
        String format = "";
        templateList.clear();
        messageSelector.getItems().clear();

        if (pacs008Radio.isSelected()) {
            format = pacs008Radio.getText();
        } else if (pacs009Radio.isSelected()) {
            format = pacs009Radio.getText();
        }

        templateList = DBConnection.getTemplateNamesByFormat(format);
        messageSelector.getItems().addAll(templateList);

    }

    public void getTemplateName(ActionEvent event) {
        this.templateName = messageSelector.getValue();
    }

    public void getFilePath(ActionEvent event) {
        this.filePath = filePathField.getText();
    }

    private void setupXmlObjectFromGui() {
        XMLFields.setFromField(fromField.getText());
        XMLFields.setToField(toField.getText());
        XMLFields.setEndToEndIdField(e2eidField.getText());
        XMLFields.setIntrBkSttlmAmtField(intrBkSttlmAmtField.getText());
        XMLFields.setIntrBkSttlmCcyAttr(intrBkSttlmCcyAttr.getText());
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
        XMLFields.setRmtInfField(detailsField.getText());
        XMLFields.setPurpCdField(detailsField.getText());
        XMLFields.setCdtrBicField(dbtrBicField.getText());
        XMLFields.setDbtrBicField(cdtrBicField.getText());
    }


    private void fillInFields(String templateName) {
        BaseFunc bf = new BaseFunc();
        String xmlContent = DBConnection.getXmlTemplateByName(templateName);
        if (xmlContent != null) {
            String from = getValueFromTemplate(xmlContent, XPaths.XFromField);
            fromField.setText(from);

            String to = getValueFromTemplate(xmlContent, XPaths.XToField);
            toField.setText(to);

            String e2e = getValueFromTemplate(xmlContent, XPaths.XEndToEndIdField);
            e2eidField.setText(e2e);

            String amt = getValueFromTemplate(xmlContent, XPaths.XIntrBkSttlmAmtField);
            intrBkSttlmAmtField.setText(amt);

            String ccy = getValueFromTemplate(xmlContent, XPaths.XIntrBkSttlmCcyAttr);
            intrBkSttlmCcyAttr.setText(ccy);

            intrBkSttlmDtField.setText(bf.today());

            String instdAgt = getValueFromTemplate(xmlContent, XPaths.XInstdAgtField);
            instdAgtField.setText(instdAgt);

            String instgAgt = getValueFromTemplate(xmlContent, XPaths.XInstgAgtField);
            instgAgtField.setText(instgAgt);

            String dbtrAgt = getValueFromTemplate(xmlContent, XPaths.XDbtrAgtField);
            dbtrAgtField.setText(dbtrAgt);

            String cdtrAgt = getValueFromTemplate(xmlContent, XPaths.XCdtrAgtField);
            cdtrAgtField.setText(cdtrAgt);

            String dbtrBic = getValueFromTemplate(xmlContent, XPaths.XDbtrBicField);
            dbtrBicField.setText(dbtrBic);

            String cdtrBic = getValueFromTemplate(xmlContent, XPaths.XCdtrBicField);
            cdtrBicField.setText(cdtrBic);

            String dbtrAcc = getValueFromTemplate(xmlContent, XPaths.XDbtrAcctField);
            dbtrAcctField.setText(dbtrAcc);

            String dbtrNm = getValueFromTemplate(xmlContent, XPaths.XDbtrNmField);
            dbtrNmField.setText(dbtrNm);

            String cdtrAcc = getValueFromTemplate(xmlContent, XPaths.XCdtrAccField);
            cdtrAccField.setText(cdtrAcc);

            String cdtrNm = getValueFromTemplate(xmlContent, XPaths.XCdtrNmField);
            cdtrNmField.setText(cdtrNm);

            String dbtrStr = getValueFromTemplate(xmlContent, XPaths.XDbtrStrtNmField);
            dbtrStrField.setText(dbtrStr);

            String cdtrStr = getValueFromTemplate(xmlContent, XPaths.XCdtrStrtNmField);
            cdtrStrField.setText(cdtrStr);

            String dbtrCtry = getValueFromTemplate(xmlContent, XPaths.XDbtrCtryField);
            dbtrCtryField.setText(dbtrCtry);

            String dbtrTwn = getValueFromTemplate(xmlContent, XPaths.XDbtrTwnNmField);
            dbtrTownField.setText(dbtrTwn);

            String cdtrCtry = getValueFromTemplate(xmlContent, XPaths.XCdtrCtryField);
            cdtrCtryField.setText(cdtrCtry);

            String cdtrTwn = getValueFromTemplate(xmlContent, XPaths.XCdtrTwnNmField);
            cdtrTownField.setText(cdtrTwn);

            String details = getValueFromTemplate(xmlContent, XPaths.XRmtInfField);
            detailsField.setText(details);

//            String purpCd = getValueFromTemplate(xmlContent, XPaths.XPurposeCode);
//            detailsField.setText(purpCd);
        }
    }


    private String getValueFromTemplate(String xmlTemplate, String fieldXPath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(xmlTemplate));
            Document document = builder.parse(inputSource);
            XPath xPath = XPathFactory.newInstance().newXPath();

            return xPath.evaluate(fieldXPath, document);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @FXML
    protected void onGenXmlButtonClick() {
        this.filePath = filePathField.getText();
        setupXmlObjectFromGui();
        String generatedXml = xmlModifier.prepareXml(templateName);

        XSDValidator xsdValidator = new XSDValidator();
        if (true/*xsdValidator.validateXml(generatedXml)*/) {
            fileGenerator.generateXmlFile(generatedXml, templateName, filePath);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "File is created!", ButtonType.OK);
            alert.showAndWait();
        }
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

        XSDValidator xsdValidator = new XSDValidator();

        if (selectedFile != null) {
            try {
                String fileName = selectedFile.getName();
                String xml = new String(Files.readAllBytes(Paths.get(selectedFile.getAbsolutePath())));


                if (//xsdValidator.validateXml(xml) &&
                        DBConnection.getXmlTemplateByName(fileName) == null) {

                    DBConnection.saveNewTemplate(fileName, xml);
                    refreshListOfTemplates();

                    Alert alert = new Alert(Alert.AlertType.NONE, "New template added", ButtonType.OK);
                    alert.show();
                } else {

                    Alert alert = new Alert(Alert.AlertType.ERROR, "Try something else!", ButtonType.OK);
                    alert.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
