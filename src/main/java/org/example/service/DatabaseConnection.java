package org.example.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final Connection con;

    static {
        try {
            String url = ConfigManager.getProperty("db.url");
            String user = ConfigManager.getProperty("db.user");
            String password = ConfigManager.getProperty("db.password");
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing database connection");
        }
    }

    public static Connection getConnection() {
        if (con == null) {
            throw new RuntimeException("Connection is not initialized.");
        }
        return con;
    }
}

