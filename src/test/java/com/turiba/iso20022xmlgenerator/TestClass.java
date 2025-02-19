package com.turiba.iso20022xmlgenerator;

import com.turiba.iso20022xmlgenerator.database.DBConnection;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestClass {
    BaseFunc bf = new BaseFunc();

    @Test
    public void canGenerateUniqueString() {
        String s1 = bf.generateUniqueString();
        String s2 = bf.generateUniqueString();
        assertFalse(s1.equals(s2));
    }

    @Test
    public void dateMatchesRegex() {
        String today = bf.today();
        assertTrue(today.matches(("(20)\\d{2}-(0[1-9]|1[1,2])-(0[1-9]|[12][0-9]|3[01])")));
    }

    @Test
    public void canGetTemplateByNameFromDB() {
        String pacs008 = DBConnection.getXmlTemplateByName("pacs.008 default template");
        String pacs009 = DBConnection.getXmlTemplateByName("pacs.009 default template");
        assertFalse(pacs008.isBlank());
        assertFalse(pacs009.isBlank());
    }

    @Test
    public void canGetXsdForMesTypeFromDB() {
        String[] xsdTypes = {"pacs.008.001.08", "pacs.009.001.08", "envelope008", "envelope009"};
        for (String type : xsdTypes) {
            String schema = DBConnection.getXsdForMesType(type);
            assertFalse(schema.isBlank());
        }
    }

    @Test
    public void canGetTemplateNamesByFormatFromDB() {
        String[] pacsFormats = {"pacs.008.001.08", "pacs.009.001.08"};
        List<String> messageTypes = new ArrayList<>();
        List<String> allTypes = new ArrayList<>();
        for (String format : pacsFormats) {
            messageTypes = DBConnection.getTemplateNamesByFormat(format);
            for (String type : messageTypes) {
                allTypes.add(type);
            }
        }
        assertTrue(allTypes.contains("pacs.008 default template"));
        assertTrue(allTypes.contains("pacs.009 default template"));
    }

    @Test
    public void canValidateXml() {
        XSDValidator xsdValidator = new XSDValidator();
        String validXml = DBConnection.getXmlTemplateByName("pacs.008 default template");
        String invalidXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        assertTrue(xsdValidator.validateXml(validXml));
        assertFalse(xsdValidator.validateXml(invalidXml));
    }
}
