package com.Y_LAB.homework.dao.training;

import com.Y_LAB.homework.Main;
import com.Y_LAB.homework.entity.trainings.AdditionalData;
import com.Y_LAB.homework.service.training.TrainingServiceImpl;
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

class AdditionalDataDAOImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest");
    private Connection connection;

    private final AdditionalDataDAO additionalDataDAO = new AdditionalDataDAOImpl();

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
    public void getAllAdditionalData() throws SQLException {

        int actual = TrainingServiceImpl.getService().getTraining(2).getAdditionalDataMap().size();
        additionalDataDAO.saveAdditionalData("Раз", "150", 2);

        int expected = TrainingServiceImpl.getService().getTraining(2).getAdditionalDataMap().size();

        assertThat(actual).isEqualTo(expected - 1);
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM training_diary.additional_data WHERE " +
                "name = 'Раз' AND value = '150' AND training_id = 2");
    }
    @Test
    public void getAdditionalData(){
        AdditionalData additionalData = additionalDataDAO.getAdditionalData("Количество", "30", 1);

        assertThat(additionalData.getId()).isEqualTo(1);
    }

    @Test
    public void saveAdditionalData(){
        additionalDataDAO.saveAdditionalData("Скорость", "100", 5);

        AdditionalData additionalData = additionalDataDAO.getAdditionalData("Скорость", "100", 5);

        assertThat(additionalData).isNotNull();
    }

    @Test
    public void updateAdditionalData(){
        AdditionalData additionalData = additionalDataDAO.getAdditionalData("Количество", "50", 3);
        additionalData.setValue("100");

        additionalDataDAO.updateAdditionalData(additionalData);

        assertThat(additionalData).isEqualTo(additionalDataDAO.getAdditionalData("Количество", "100", 3));
        additionalData.setValue("50");
        additionalDataDAO.updateAdditionalData(additionalData);
    }

    @Test
    public void deleteAdditionalData(){
        additionalDataDAO.saveAdditionalData("Kol", "1000", 5);
        AdditionalData additionalData = additionalDataDAO.getAdditionalData("Kol", "1000", 5);

        additionalDataDAO.deleteAdditionalData(additionalData.getId());

        assertThat(additionalDataDAO.getAdditionalData("Kol", "1000", 5)).isNull();
    }

}