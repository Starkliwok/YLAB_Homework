package com.Y_LAB.homework;

import com.Y_LAB.homework.util.db.ConnectionToDatabase;
import com.Y_LAB.homework.util.init.PropertiesLoader;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class LiquibaseEx {

    public static void main(String[] args) {
        try {
            Properties properties = PropertiesLoader.getProperties("src/main/resources/application.properties");
            Connection connection = ConnectionToDatabase.getConnection();

            Statement statement = connection.createStatement();
            statement.execute("CREATE SCHEMA IF NOT EXISTS training_dairy_service");
            statement.close();

            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase =
                    new Liquibase(properties.getProperty("changeLogFile"), new ClassLoaderResourceAccessor(), database);
            liquibase.update();
        } catch (DatabaseException e) {
            System.out.println("Database Exception in migration " + e.getMessage());
        } catch (LiquibaseException e) {
            System.out.println("Liquibase Exception in migration " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
