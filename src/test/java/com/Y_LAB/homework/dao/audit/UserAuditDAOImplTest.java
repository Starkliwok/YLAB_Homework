package com.Y_LAB.homework.dao.audit;

import com.Y_LAB.homework.audit.UserAuditResult;
import com.Y_LAB.homework.entity.UserAudit;
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
class UserAuditDAOImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:latest")
            .withExposedPorts(5433, 5432)
            .withUsername("postgres")
            .withPassword("starkliw")
            .withDatabaseName("postgres");
    private static Connection connection;

    private static UserAuditDAO userAudit;

    @BeforeAll
    static void beforeAll() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("url", postgresContainer.getJdbcUrl());
        properties.setProperty("username", postgresContainer.getUsername());
        properties.setProperty("password", postgresContainer.getPassword());

        LiquibaseConfig.initMigration(ConnectionToDatabase.getConnectionFromProperties(properties));
        connection = ConnectionToDatabase.getConnectionFromProperties(properties);
        connection.setAutoCommit(false);
        userAudit = new UserAuditDAOImpl(connection);
    }

    @Test
    @DisplayName("Увеличение количества всех аудитов после сохранения аудита")
    public void getAllUserAudits() {
        int actual = userAudit.getAllUserAudits().size();
        userAudit.saveUserAudit(1L, "some action", UserAuditResult.SUCCESS);

        int expected = userAudit.getAllUserAudits().size();

        assertThat(actual).isEqualTo(expected - 1);
    }

    @Test
    @DisplayName("Получение аудита по идентификационному номеру")
    public void getUserAudit() throws SQLException {
        userAudit.saveUserAudit(2L, "some action", UserAuditResult.SUCCESS);
        Statement statement = connection.createStatement();
        statement.execute("SELECT * FROM training_diary.user_audit WHERE " +
                "action = 'some action' AND user_id = 2");
        ResultSet rs = statement.getResultSet();
        rs.next();

        UserAudit userAudit = UserAuditDAOImplTest.userAudit.getUserAudit(rs.getLong(1));

        assertThat(userAudit.action()).isEqualTo("some action");
        assertThat(userAudit.userId()).isEqualTo(2L);
        assertThat(userAudit.userAuditResult()).isEqualTo(UserAuditResult.SUCCESS.name());
    }

    @Test
    @DisplayName("Сохранение аудита и проверка на наличие новой записи в бд")
    public void saveUserAudit() throws SQLException {
        int actual = userAudit.getAllUserAudits().size();

        userAudit.saveUserAudit(1L, "some action", UserAuditResult.FAIL);
        Statement statement = connection.createStatement();
        statement.execute("SELECT * FROM training_diary.user_audit WHERE " +
                "action = 'some action' AND user_id = 1");
        ResultSet rs = statement.getResultSet();
        rs.next();

        assertThat(actual + 1).isEqualTo(userAudit.getAllUserAudits().size());
        assertThat(1).isEqualTo(rs.getLong(3));
        assertThat("some action").isEqualTo(rs.getString(4));
    }
}