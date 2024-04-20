package com.Y_LAB.homework.dao.user;

import com.Y_LAB.homework.Main;
import com.Y_LAB.homework.entity.User;
import com.Y_LAB.homework.exception.auth.WrongUsernameAndPasswordException;
import com.Y_LAB.homework.util.db.ConnectionToDatabase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

class UserDAOImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest");
    private Connection connection;

    private final UserDAO userDAO = new UserDAOImpl();

    @BeforeAll
    public static void setUp() {
        Main.initApplication();
        postgresContainer.start();
    }

    @BeforeEach
    public void connectToDatabase() {
        connection = ConnectionToDatabase.getConnection();
    }

    @Test
    public void getAllUsers() throws SQLException {
        int actual = userDAO.getAllUsers().size();
        userDAO.saveUser("example", "example");

        int expected = userDAO.getAllUsers().size();

        assertThat(actual).isEqualTo(expected - 1);
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM training_diary.users WHERE " +
                "name = 'example' AND password = 'example'");
    }

    @Test
    public void getUser() throws WrongUsernameAndPasswordException {
        userDAO.saveUser("kool", "kool");

        User user = userDAO.getUser("kool", "kool");

        assertThat(user.getUsername()).isEqualTo("kool");
        assertThat(user.getPassword()).isEqualTo("kool");
        userDAO.deleteUser(user.getId());
    }

    @Test
    public void saveUser() throws WrongUsernameAndPasswordException {
        int actual = userDAO.getAllUsers().size();

        userDAO.saveUser("some user", "some password");

        assertThat(actual).isEqualTo(userDAO.getAllUsers().size() - 1);
        userDAO.deleteUser(userDAO.getUser("some user", "some password").getId());
    }

    @Test
    public void updateUser() throws WrongUsernameAndPasswordException {
        User user = userDAO.getUser("root", "root");
        user.setPassword("root2");

        userDAO.updateUser(user);

        assertThat(user).isEqualTo(userDAO.getUser("root", "root2"));
        user.setPassword("root");
        userDAO.updateUser(user);
    }

    @Test
    public void deleteUser() throws WrongUsernameAndPasswordException {
        userDAO.saveUser("some user", "some user");
        User user = userDAO.getUser("some user", "some user");

        userDAO.deleteUser(user.getId());
        User user2 = null;
        try {
            user2 = userDAO.getUser("some user", "some user");
        } catch (WrongUsernameAndPasswordException ignored) {}

        assertThat(user2).isNull();
    }

}