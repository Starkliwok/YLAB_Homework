package com.Y_LAB.homework.dao.audit;

import com.Y_LAB.homework.Main;
import com.Y_LAB.homework.audit.UserAuditResult;
import com.Y_LAB.homework.entity.UserAudit;
import com.Y_LAB.homework.util.db.ConnectionToDatabase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

class UserAuditDAOImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest");
    private Connection connection;

    private final UserAuditDAO userAudit = new UserAuditDAOImpl();

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
    public void getAllUserAudits() throws SQLException {
        int actual = userAudit.getAllUserAudits().size();
        userAudit.saveUserAudit(1000L, "some action", UserAuditResult.SUCCESS);

        int expected = userAudit.getAllUserAudits().size();

        assertThat(actual).isEqualTo(expected - 1);
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM training_diary.user_audit WHERE " +
                "action = 'some action' AND user_id = 1000");
    }

    @Test
    public void getUserAudit() throws SQLException {
        userAudit.saveUserAudit(1000L, "some action", UserAuditResult.SUCCESS);
        Statement statement = connection.createStatement();
        statement.execute("SELECT * FROM training_diary.user_audit WHERE " +
                "action = 'some action' AND user_id = 1000");
        ResultSet rs = statement.getResultSet();
        rs.next();

        UserAudit userAudit = this.userAudit.getUserAudit(rs.getLong(1));

        assertThat(userAudit.action()).isEqualTo("some action");
        assertThat(userAudit.userId()).isEqualTo(1000L);
        assertThat(userAudit.userAuditResult()).isEqualTo(UserAuditResult.SUCCESS.name());
        statement = connection.createStatement();
        statement.execute("DELETE FROM training_diary.user_audit WHERE " +
                "action = 'some action' AND user_id = 1000");
    }

    @Test
    public void saveUserAudit() throws SQLException {
        int actual = userAudit.getAllUserAudits().size();

        userAudit.saveUserAudit(10000L, "some action", UserAuditResult.FAIL);

        assertThat(actual).isEqualTo(userAudit.getAllUserAudits().size() - 1);
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM training_diary.user_audit WHERE " +
                "action = 'some action' AND user_id = 10000");
    }
}