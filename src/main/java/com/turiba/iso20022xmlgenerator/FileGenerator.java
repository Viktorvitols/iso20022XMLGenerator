package com.turiba.iso20022xmlgenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FileGenerator {
    private final LocalTime time = LocalTime.now();
    private final String fileNameTime = time.format(DateTimeFormatter.ofPattern("H-m-s-n"));

    public void generateXmlFile(String xml, String messageName) {
        File xmlFile = null;
        try {
            xmlFile = new File(messageName + "_" + fileNameTime + ".xml");
            if (xmlFile.createNewFile()) {
                System.out.println("File created: " + xmlFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(xmlFile.getName());
            myWriter.write(xml);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
