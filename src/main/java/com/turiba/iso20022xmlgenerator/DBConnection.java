package com.turiba.iso20022xmlgenerator;

import java.sql.*;

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
                + " xmlTemplate TEXT NOT NULL,\n"
                + " xsd TEXT);";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("XMLTEMPLATES table is created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}