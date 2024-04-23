package com.Y_LAB.homework.util.init;

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

/**
 * Класс для конфигурирования миграций баз данных
 * @author Денис Попов
 * @version 2.0
 */
public class LiquibaseConfig {

    private LiquibaseConfig() {}

    /**
     * Метод конфигурирует миграции баз данных.
     * @param connection объект, по которому будет происходить подключение к базе данных
     */
    public static void initMigration(Connection connection) {
        try {
            Properties properties = PropertiesLoader.getProperties("src/main/resources/application.properties");
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
