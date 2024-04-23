package com.Y_LAB.homework.util.db;

import com.Y_LAB.homework.util.init.PropertiesLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Класс для подключения к базе данных
 * @author Денис Попов
 * @version 2.0
 */
public class ConnectionToDatabase {

    /** Поле подключения к базе данных*/
    private static Connection conn;

    private ConnectionToDatabase(){}

    /**
     * Метод возвращает подключение, в случае если созданного подключения не существует или оно закрыто.
     * Вызывает метод {@link PropertiesLoader#getProperties(String)} для получения параметров для создания подключения
     * @return подключение к базе данных
     * @throws RuntimeException в случае проблем с созданием подключения
     */
    public static Connection getConnection(){
        Properties properties = PropertiesLoader.getProperties("src/main/resources/application.properties");
        try {
            if (conn == null || conn.isClosed()) {
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

    /**
     * Метод возвращает подключение, которое создает благодаря получаемым данным из объекта {@link Properties}
     * Вызывает методы {@link Properties#getProperty(String)} для получения параметров для создания подключения
     * @return подключение к базе данных
     * @throws RuntimeException в случае проблем с созданием подключения
     */
    public static Connection getConnectionFromProperties(Properties properties){
        Connection connection;
        try {
            connection = DriverManager.getConnection(
                    properties.getProperty("url")
                    , properties.getProperty("username")
                    , properties.getProperty("password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}