package com.Y_LAB.homework.dao.training;

import com.Y_LAB.homework.Main;
import com.Y_LAB.homework.entity.trainings.Training;
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
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TrainingDAOImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest");
    private Connection connection;

    private final TrainingDAO trainingDAO = new TrainingDAOImpl();

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
    public void getAllTrainings(){
        int size = trainingDAO.getAllTrainings(4).size();
        trainingDAO.saveTraining(new Training(100, "some", "sa", new Date(1220227200),
                0, 0, 4));

        List<Training> trainings = trainingDAO.getAllTrainings(4);

        assertThat(trainings.size()).isEqualTo(size + 1);
    }

    @Test
    public void getTraining(){
        Training training = trainingDAO.getTraining(1);

        assertThat(training.getId()).isEqualTo(1);
        assertThat(training.getUserId()).isEqualTo(1);
    }

    @Test
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
        trainingDAO.deleteTraining(rs.getLong(1));
    }

    @Test
    public void updateTraining(){
        Training actual = trainingDAO.getTraining(1);
        String type = actual.getType();
        actual.setType("some type");

        trainingDAO.updateTraining(actual);
        Training expected = trainingDAO.getTraining(1);

        assertThat(actual).isEqualTo(expected);
        actual.setType(type);
        trainingDAO.updateTraining(actual);
    }

    @Test
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
}