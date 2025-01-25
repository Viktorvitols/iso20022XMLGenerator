package com.turiba.iso20022xmlgenerator.database;

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

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS XMLTEMPLATES (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " messageType TEXT NOT NULL,\n"
                + " xmlTemplate TEXT NOT NULL;";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement statement = conn.createStatement()) {
            statement.execute(sql);
            System.out.println("XMLTEMPLATES table is created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getXmlTemplate(String name) {

        String template = null;
        String sql = "SELECT xmlTemplate from XMLTEMPLATES WHERE templateName = " + name;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            if (rs.next()){
                template = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return template;
    }


    public static String getXsdForMes(String mesType) {

        String envelope = null;
        String header = null;
        String document = null;
        String sql = "SELECT * from XSDSCHEMA WHERE messageType = " + mesType;

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            if (rs.next()){
                envelope = rs.getString(2);
                header = rs.getString(3);
                document = rs.getString(4);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return "";
    }


    public static List<String> getMessageTypes() {
        List<String> messageTypes = new ArrayList<>();;
        String sql = "SELECT messageType FROM XMLTEMPLATES;";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {
                messageTypes.add(rs.getString(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return messageTypes;
    }

}