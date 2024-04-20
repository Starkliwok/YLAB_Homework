package com.Y_LAB.homework;

import com.Y_LAB.homework.in.user_panel.HomePanel;
import com.Y_LAB.homework.util.db.ConnectionToDatabase;
import com.Y_LAB.homework.util.init.PropertiesLoader;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class Main {
    public static void main(String[] args) {
        initApplication();

        HomePanel.printStartPage();
    }
    public static void initApplication() {
        try {
            Properties properties = PropertiesLoader.getProperties("src/main/resources/application.properties");
            Connection connection = ConnectionToDatabase.getConnection();

            Statement statement = connection.createStatement();
            statement.execute("CREATE SCHEMA IF NOT EXISTS training_diary_service");
            statement.close();

            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase =
                    new Liquibase(properties.getProperty("changeLogFile"), new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            connection.close();
        } catch (LiquibaseException | SQLException e) {
            System.out.println("Произошла ошибка, приложение завершает работу");
            System.exit(-1);
        }
    }
}