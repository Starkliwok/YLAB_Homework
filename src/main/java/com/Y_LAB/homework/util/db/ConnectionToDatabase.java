package com.Y_LAB.homework.util.db;

import com.Y_LAB.homework.util.init.PropertiesLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionToDatabase {
    private static Connection conn;

    private ConnectionToDatabase(){}
    public static Connection getConnection(){
        try {
            if (conn == null || conn.isClosed()) {
                Properties properties =
                        PropertiesLoader.getProperties("src/main/resources/application.properties");

                conn = DriverManager.getConnection
                        (properties.getProperty("url")
                        , properties.getProperty("username")
                        , properties.getProperty("password"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}