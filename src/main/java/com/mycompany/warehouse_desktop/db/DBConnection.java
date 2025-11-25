package com.mycompany.warehouse_desktop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {


    private static final String URL = "jdbc:mysql://tramway.proxy.rlwy.net:15770/railway";
    private static final String USER = "root";
    private static final String PASSWORD = "jRtMLjoACtxozJtkKfWVliQtPtTAVhKe";

    private static DBConnection instance;

    private DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Load driver MySQL
        } catch (ClassNotFoundException e) {
            System.err.println(" MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }

    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
