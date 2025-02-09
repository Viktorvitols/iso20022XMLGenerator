package com.turiba.iso20022xmlgenerator.database;

import com.turiba.iso20022xmlgenerator.XSDValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:ISOGenDB/ISOGenDB.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static String getXmlTemplateByName(String templateName) {
        String sql = "SELECT XML_TEMPLATE from XMLTEMPLATE WHERE TEMPLATE_NAME = ?;";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, templateName);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("XML_TEMPLATE");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String getXsdForMesType(String mesType) {

        String sql = "SELECT * from XSDSCHEMA WHERE FOR_MESSAGE = ?;";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, mesType);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return rs.getString("XSD_SCHEMA");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAppHeaderXSD() {
        return getXsdForMesType("head.001.001.02");
    }

    public static String getEnvelopeXSD() {
        return getXsdForMesType("envelope");
    }

    public static String getDocumentXSD(String pacsType) {
        return getXsdForMesType(pacsType);
    }

    public static List<String> getTemplateNames() {
        List<String> messageTypes = new ArrayList<>();

        String sql = "SELECT TEMPLATE_NAME FROM XMLTEMPLATE;";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                messageTypes.add(rs.getString("TEMPLATE_NAME"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return messageTypes;
    }


    public static void saveNewTemplate(String templateName, String message) {
        XSDValidator xsdValidator = new XSDValidator();
        String mesType = xsdValidator.getMessageFormat(message);

        String sql = "INSERT INTO XMLTEMPLATE (TEMPLATE_NAME, XML_TEMPLATE, MESSAGE_TYPE)\n" +
                "VALUES (?, ?, ?);";

        if (mesType == null) {
            throw new NullPointerException("Unknown message");
        }

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, templateName);
            statement.setString(2, message);
            statement.setString(3, mesType);
            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}