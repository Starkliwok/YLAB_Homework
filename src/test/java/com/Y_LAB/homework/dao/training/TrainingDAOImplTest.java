package com.Y_LAB.homework.dao.training;

import com.Y_LAB.homework.entity.trainings.Training;
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
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class TrainingDAOImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withExposedPorts(5433, 5432)
                    .withUsername("postgres")
                    .withPassword("starkliw")
                    .withDatabaseName("postgres");
    private static Connection connection;

    private static TrainingDAO trainingDAO;

    @BeforeAll
    static void beforeAll() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("url", postgresContainer.getJdbcUrl());
        properties.setProperty("username", postgresContainer.getUsername());
        properties.setProperty("password", postgresContainer.getPassword());

        LiquibaseConfig.initMigration(ConnectionToDatabase.getConnectionFromProperties(properties));
        connection = ConnectionToDatabase.getConnectionFromProperties(properties);
        connection.setAutoCommit(false);
        trainingDAO = new TrainingDAOImpl(connection);
    }

    @Test
    @DisplayName("Проверка на увеличение общего количества тренировок при добавлении новой записи")
    public void getAllTrainings(){
        int size = trainingDAO.getAllTrainings(4).size();
        trainingDAO.saveTraining(new Training(100, "some", "sa", new Date(1220227200),
                0, 0, 4));

        List<Training> trainings = trainingDAO.getAllTrainings(4);

        assertThat(trainings.size()).isEqualTo(size + 1);
    }

    @Test
    @DisplayName("Получение тренировки из бд")
    public void getTraining(){
        Training training = trainingDAO.getTraining(1);

        assertThat(training.getId()).isEqualTo(1);
        assertThat(training.getUserId()).isEqualTo(1);
    }

    @Test
    @DisplayName("Сохранение тренировки и проверка на наличие новой записи в бд")
    public void saveTraining() throws SQLException {
        Training actual = new Training();
        actual.setName("some ");
        actual.setType("sas");
        actual.setDate(new Date(1220227200));
        actual.setUserId(5);

        trainingDAO.saveTraining(actual);
        Statement statement = connection.createStatement();
        statement.execute("SELECT * FROM training_diary.trainings WHERE " +
                "name = 'some ' AND type = 'sas' AND calories_spent = 0 AND duration_in_minutes = 0 AND user_id = 5");
        ResultSet rs = statement.getResultSet();
        Training expected = new Training();
        rs.next();
        expected.setName(rs.getString(2));
        expected.setType(rs.getString(3));
        expected.setDate(new Date(1220227200));
        expected.setUserId(rs.getLong(7));

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Обновление тренировки и проверка на обновление записи в бд")
    public void updateTraining(){
        Training actual = trainingDAO.getTraining(1);
        actual.setType("some type");

        trainingDAO.updateTraining(actual);
        Training expected = trainingDAO.getTraining(1);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Удаление тренировки и проверка на отсутствие записи в бд")
    public void deleteTraining() throws SQLException {
        Training actual = new Training();
        actual.setName("some ");
        actual.setType("sas");
        actual.setDate(new Date(1220227200));
        actual.setUserId(5);

        trainingDAO.saveTraining(actual);
        Statement statement = connection.createStatement();
        statement.execute("SELECT * FROM training_diary.trainings WHERE " +
                "name = 'some ' AND type = 'sas' AND calories_spent = 0 AND duration_in_minutes = 0 AND user_id = 5");
        ResultSet rs = statement.getResultSet();
        Training expected = new Training();
        rs.next();
        long id = rs.getLong(1);
        expected.setName(rs.getString(2));
        expected.setType(rs.getString(3));
        expected.setDate(new Date(1220227200));
        expected.setUserId(rs.getLong(7));

        assertThat(actual).isEqualTo(expected);
        trainingDAO.deleteTraining(id);
        expected = trainingDAO.getTraining(id);
        assertThat(expected).isNull();
    }

    @Test
    @DisplayName("Получение тренировки из бд")
    void getTrainingId() throws SQLException {
        Training test = new Training();
        test.setName("test ");
        test.setType("test");
        test.setDate(new Date(1220227200));
        test.setUserId(5);

        trainingDAO.saveTraining(test);
        Statement statement = connection.createStatement();
        statement.execute("SELECT id FROM training_diary.trainings WHERE " +
                "name = 'test ' AND type = 'test' AND calories_spent = 0 AND duration_in_minutes = 0 AND user_id = 5");
        ResultSet rs = statement.getResultSet();
        rs.next();
        long expected = rs.getLong(1);
        long actual = trainingDAO.getTrainingId("test ", "test",
                new Date(1220227200), 0, 0, 5);

        assertThat(actual).isEqualTo(expected);
    }
}