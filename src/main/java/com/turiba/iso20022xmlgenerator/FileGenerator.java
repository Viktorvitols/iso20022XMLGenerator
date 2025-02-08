package com.turiba.iso20022xmlgenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Paths;

public class FileGenerator {

    BaseFunc bf = new BaseFunc();

    public void generateXmlFile(String xml, String templateName, String path) {
        if ("".equals(path)) {
            System.out.println("No path provided");
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
            System.out.println("File already exists.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void writeToFile(File xmlFile, String xml) {
        try {
            FileWriter writer = new FileWriter(xmlFile);
            writer.write(xml);
            writer.close();
            System.out.println("File is created successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
