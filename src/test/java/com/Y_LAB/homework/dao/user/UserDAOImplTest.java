package com.Y_LAB.homework.dao.user;

import com.Y_LAB.homework.entity.User;
import com.Y_LAB.homework.exception.auth.WrongUsernameAndPasswordException;
import com.Y_LAB.homework.util.db.ConnectionToDatabase;
import com.Y_LAB.homework.util.init.LiquibaseConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class UserDAOImplTest {
    @Container
    private static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withExposedPorts(5433, 5432)
                    .withUsername("postgres")
                    .withPassword("starkliw")
                    .withDatabaseName("postgres");
    private static Connection connection;

    private static UserDAO userDAO;

    @BeforeAll
    static void beforeAll() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("url", postgresContainer.getJdbcUrl());
        properties.setProperty("username", postgresContainer.getUsername());
        properties.setProperty("password", postgresContainer.getPassword());

        LiquibaseConfig.initMigration(ConnectionToDatabase.getConnectionFromProperties(properties));
        connection = ConnectionToDatabase.getConnectionFromProperties(properties);
        connection.setAutoCommit(false);
        userDAO = new UserDAOImpl(connection);
    }

    @Test
    @DisplayName("Проверка на увеличение общего пользователей при добавлении новой записи")
    public void getAllUsers(){
        int actual = userDAO.getAllUsers().size();
        userDAO.saveUser("example", "example");

        int expected = userDAO.getAllUsers().size();

        assertThat(actual).isEqualTo(expected - 1);
    }

    @Test
    @DisplayName("Получение тренировки из бд")
    public void getUser() throws WrongUsernameAndPasswordException {
        userDAO.saveUser("kool", "koolov");

        User user = userDAO.getUser("kool", "koolov");

        assertThat(user.getUsername()).isEqualTo("kool");
        assertThat(user.getPassword()).isEqualTo("koolov");
    }

    @Test
    @DisplayName("Сохранение пользователя и проверка на наличие новой записи в бд")
    public void saveUser() throws SQLException {
        int actual = userDAO.getAllUsers().size();

        userDAO.saveUser("some user", "some password");
        Statement statement = connection.createStatement();
        statement.execute("SELECT * FROM training_diary.users WHERE " +
                "name = 'some user' AND password = 'some password'");
        ResultSet rs = statement.getResultSet();
        rs.next();

        assertThat(actual + 1).isEqualTo(userDAO.getAllUsers().size());
        assertThat("some user").isEqualTo(rs.getString(2));
        assertThat("some password").isEqualTo(rs.getString(3));
    }

    @Test
    @DisplayName("Обновление пользователя и проверка на обновление записи в бд")
    public void updateUser() throws WrongUsernameAndPasswordException {
        User user = userDAO.getUser("root", "root");
        user.setPassword("root2");

        userDAO.updateUser(user);

        assertThat(user).isEqualTo(userDAO.getUser("root", "root2"));
    }

    @Test
    @DisplayName("Удаление пользователя и проверка на отсутствие записи в бд")
    public void deleteUser() throws WrongUsernameAndPasswordException, SQLException {
        userDAO.saveUser("some user2", "some user");
        connection.commit();
        User user = userDAO.getUser("some user2", "some user");

        userDAO.deleteUser(user.getId());
        connection.commit();
        User user2 = null;
        try {
            user2 = userDAO.getUser("some user2", "some user");
        } catch (WrongUsernameAndPasswordException ignored) {}

        assertThat(user2).isNull();
    }

    @Test
    @DisplayName("Проверка существующих из бд и выдуманных логинов на существование в бд")
    void isUserExist() {
        boolean realUser = userDAO.isUserExist("root");
        boolean fakeUser = userDAO.isUserExist("4324324234");

        assertThat(realUser).isTrue();
        assertThat(fakeUser).isFalse();
    }
}