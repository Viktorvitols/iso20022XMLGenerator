package com.turiba.iso20022xmlgenerator;

import javafx.scene.control.Control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Paths;

public class FileGenerator {

    BaseFunc bf = new BaseFunc();

    public void generateXmlFile(String xml, String templateName, String path, Control control) {
        if ((path.isBlank())) {
            System.out.println("No path provided");
            bf.showCustomDialogMessage("Error", "No path provided", control);
            return;
        }
        String fileName = templateName + "_" + bf.generateUniqueString() + ".xml";
        String fullPath = Paths.get(path, fileName).toString();

        try {
            File xmlFile = new File(fullPath);
            if (xmlFile.createNewFile()) {
                System.out.println("File created: " + xmlFile.getName());
                System.out.println("Path: " + xmlFile.getPath());
                writeToFile(xmlFile, xml);
            } else {
                throw new FileAlreadyExistsException("File already exists.");
            }
        } catch (FileAlreadyExistsException e) {
            bf.showCustomDialogMessage("Error", "File already exists", control);
            e.printStackTrace();

        } catch (IOException e) {
            bf.showCustomDialogMessage("Error", "Error generating XML.", control);
            e.printStackTrace();
        }
    }

    public void writeToFile(File xmlFile, String xml) {
        try {
            FileWriter writer = new FileWriter(xmlFile);
            writer.write(xml);
            writer.close();
        } catch (IOException e) {
            System.out.println("Write to file error.");
            e.printStackTrace();
        }
    }
}
