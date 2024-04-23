package com.Y_LAB.homework.dao.training;

import com.Y_LAB.homework.entity.trainings.AdditionalData;
import com.Y_LAB.homework.util.db.ConnectionToDatabase;
import com.Y_LAB.homework.util.init.LiquibaseConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class AdditionalDataDAOImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withExposedPorts(5433, 5432)
                    .withUsername("postgres")
                    .withPassword("starkliw")
                    .withDatabaseName("postgres");
    private static Connection connection;

    private static AdditionalDataDAO additionalDataDAO;

    @BeforeAll
    static void beforeAll() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("url", postgresContainer.getJdbcUrl());
        properties.setProperty("username", postgresContainer.getUsername());
        properties.setProperty("password", postgresContainer.getPassword());

        LiquibaseConfig.initMigration(ConnectionToDatabase.getConnectionFromProperties(properties));
        connection = ConnectionToDatabase.getConnectionFromProperties(properties);
        connection.setAutoCommit(false);
        additionalDataDAO = new AdditionalDataDAOImpl(connection);
    }

    @Test
    @DisplayName("Проверка на увеличение общего количества дополнительной информации при добавлении новой записи")
    public void getAllAdditionalData() {
        TrainingDAO trainingService = new TrainingDAOImpl(connection);

        int actual = trainingService.getTraining(2L).getAdditionalDataMap().size();
        additionalDataDAO.saveAdditionalData("Раз", "150", 2);

        int expected = trainingService.getTraining(2L).getAdditionalDataMap().size();

        assertThat(actual).isEqualTo(expected - 1);
    }

    @Test
    @DisplayName("Получение дополнительной информации из бд")
    public void getAdditionalData() {
        AdditionalData additionalData = additionalDataDAO.getAdditionalData("Количество", "30", 1);

        assertThat(additionalData.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("Сохранение дополнительной информации и проверка на наличие новой записи в бд")
    public void saveAdditionalData() {
        additionalDataDAO.saveAdditionalData("Скорость", "100", 5);

        AdditionalData additionalData = additionalDataDAO.getAdditionalData("Скорость", "100", 5);

        assertThat(additionalData.getName()).isEqualTo("Скорость");
        assertThat(additionalData.getValue()).isEqualTo("100");
        assertThat(additionalData.getTrainingId()).isEqualTo(5);
    }

    @Test
    @DisplayName("Обновление дополнительной информации и проверка на наличие обновлённой записи в бд")
    public void updateAdditionalData() {
        AdditionalData additionalData = additionalDataDAO.getAdditionalData("Количество", "50", 3);
        additionalData.setValue("100");

        additionalDataDAO.updateAdditionalData(additionalData);

        assertThat(additionalData).isEqualTo(additionalDataDAO.getAdditionalData("Количество", "100", 3));
    }

    @Test
    @DisplayName("Удаление дополнительной информации и проверка на отсутствие записи в бд")
    public void deleteAdditionalData() throws SQLException {
        additionalDataDAO.saveAdditionalData("Kol", "1000", 5);
        AdditionalData additionalData = additionalDataDAO.getAdditionalData("Kol", "1000", 5);

        additionalDataDAO.deleteAdditionalData(additionalData.getId());
        connection.commit();

        assertThat(additionalDataDAO.getAdditionalData("Kol", "1000", 5)).isNull();
    }
}