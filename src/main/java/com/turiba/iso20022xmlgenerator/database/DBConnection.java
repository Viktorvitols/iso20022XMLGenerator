package com.turiba.iso20022xmlgenerator.database;

import com.turiba.iso20022xmlgenerator.BaseFunc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:ISOGenDB/ISOGenDB.db";

    public static Connection connect() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(URL);
            createTables(conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private static void createTables(Connection conn) {
        String tableXML = "CREATE TABLE IF NOT EXISTS XMLTEMPLATE (\n" +
                "    ID            INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                "                          NOT NULL,\n" +
                "    MESSAGE_TYPE  TEXT    NOT NULL,\n" +
                "    XML_TEMPLATE  TEXT    NOT NULL,\n" +
                "    TEMPLATE_NAME TEXT    NOT NULL\n" +
                ");";

        String tableXSD = "CREATE TABLE IF NOT EXISTS XSDSCHEMA (\n" +
                "    ID          INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                "                        NOT NULL,\n" +
                "    FOR_MESSAGE TEXT    NOT NULL,\n" +
                "    XSD_SCHEMA  TEXT    NOT NULL\n" +
                ");";

        try (Statement statement = conn.createStatement()){
            statement.execute(tableXSD);
            statement.execute(tableXML);
        }catch (SQLException e) {
            e.printStackTrace();
        }
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
        return "";
    }


    public static String getXsdForMesType(String mesType) {
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

    public static List<String> getTemplateNamesByFormat(String pacsFormat) {
        List<String> messageTypes = new ArrayList<>();
        String sql = "SELECT TEMPLATE_NAME FROM XMLTEMPLATE WHERE MESSAGE_TYPE = ?;";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, pacsFormat);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                messageTypes.add(rs.getString("TEMPLATE_NAME"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return messageTypes;
    }


    public static void saveNewTemplate(String templateName, String message) {
        BaseFunc bf = new BaseFunc();
        String mesType = bf.getMessageFormat(message);
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean deleteTemplate(String templateName) {
        String sql = "DELETE FROM XMLTEMPLATE WHERE TEMPLATE_NAME = ?;";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, templateName);
            return (statement.executeUpdate() > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}