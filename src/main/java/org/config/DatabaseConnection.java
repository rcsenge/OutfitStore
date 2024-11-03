package org.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = ConfigManager.getProperty("db.url");
            String user = ConfigManager.getProperty("db.user");
            String password = ConfigManager.getProperty("db.password");
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Kapcsolat sikeresen létrejött!");
            return con;  // Visszaadunk egy új Connection-t
        } catch (SQLException e) {
            System.out.println("Kapcsolat nem jött létre!");
            throw new RuntimeException("Error initializing database connection: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC driver not found.");
            throw new RuntimeException("Error loading MySQL JDBC driver: " + e.getMessage(), e);
        }
    }
}
