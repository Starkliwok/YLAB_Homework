package com.Y_LAB.homework.service.training;

import com.Y_LAB.homework.dao.training.AdditionalDataDAOImpl;
import com.Y_LAB.homework.dao.training.TrainingDAOImpl;
import com.Y_LAB.homework.entity.trainings.AdditionalData;
import com.Y_LAB.homework.entity.trainings.Training;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {
    @Mock
    private AdditionalDataDAOImpl additionalDataDAO;

    @Mock
    private TrainingDAOImpl trainingDAO;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Test
    @DisplayName("Вызов метода получения всех дополнительных информаций тренировки")
    void getAllAdditionalData() {
        long id = 1;

        trainingService.getAllAdditionalData(id);

        verify(additionalDataDAO, times(1)).getAllAdditionalData(id);
    }

    @Test
    @DisplayName("Вызов метода получения дополнительной информации тренировки")
    void getAdditionalData() {
        String name = "Скорость";
        String value = "200";
        long trainingId = 1L;

        trainingService.getAdditionalData(name, value, trainingId);

        verify(additionalDataDAO, times(1)).getAdditionalData(name, value, trainingId);
    }

    @Test
    @DisplayName("Вызов метода сохранения дополнительной информации тренировки")
    void saveAdditionalData() {
        String name = "Скорость";
        String value = "200";
        long trainingId = 1L;

        trainingService.saveAdditionalData(name, value, trainingId);

        verify(additionalDataDAO, times(1)).saveAdditionalData(name, value, trainingId);
    }

    @Test
    @DisplayName("Вызов метода обновления дополнительной информации тренировки")
    void updateAdditionalData() {
        long id = 10L;
        String name = "Скорость";
        String value = "200";
        long trainingId = 1L;
        AdditionalData additionalData = new AdditionalData(id, name, value, trainingId);

        trainingService.updateAdditionalData(additionalData);

        verify(additionalDataDAO, times(1)).updateAdditionalData(additionalData);
    }

    @Test
    @DisplayName("Вызов метода удаления дополнительной информации тренировки")
    void deleteAdditionalData() {
        long id = 10L;

        trainingService.deleteAdditionalData(id);

        verify(additionalDataDAO, times(1)).deleteAdditionalData(id);
    }

    @Test
    @DisplayName("Вызов получения всех тренировок пользователя")
    void getAllTrainings() {
        long userId = 1L;

        trainingService.getAllTrainings(userId);

        verify(trainingDAO, times(1)).getAllTrainings(userId);
    }

    @Test
    @DisplayName("Вызов метода получения тренировки по идентификационному номеру")
    void getTraining() {
        long id = 1L;

        trainingService.getTraining(id);

        verify(trainingDAO, times(1)).getTraining(id);
    }

    @Test
    @DisplayName("Вызов метода получения идентификационного номера тренировки")
    void getTrainingId() {
        String name = "some name";
        String type = "some type";
        Date date = new Date(1220227200);
        int caloriesSpent = 100;
        double durationInMinutes = 10.0;
        long userId = 1L;

        trainingService.getTrainingId(name, type, date, caloriesSpent, durationInMinutes, userId);

        verify(trainingDAO, times(1))
                .getTrainingId(name, type, date, caloriesSpent, durationInMinutes, userId);
    }

    @Test
    @DisplayName("Вызов метода сохранения тренировки")
    void saveTraining() {
        long id = 100L;
        String name = "some name";
        String type = "some type";
        Date date = new Date(1220227200);
        int caloriesSpent = 100;
        double durationInMinutes = 10.0;
        long userId = 1L;
        Training training = new Training(id, name, type, date, caloriesSpent, durationInMinutes, userId);

        trainingService.saveTraining(training);

        verify(trainingDAO, times(1)).saveTraining(training);
    }

    @Test
    @DisplayName("Вызов метода обновления тренировки")
    void updateTraining() {
        long id = 100L;
        String name = "some name";
        String type = "some type";
        Date date = new Date(1220227200);
        int caloriesSpent = 100;
        double durationInMinutes = 10.0;
        long userId = 1L;
        Training training = new Training(id, name, type, date, caloriesSpent, durationInMinutes, userId);

        trainingService.updateTraining(training);

        verify(trainingDAO, times(1)).updateTraining(training);
    }

    @Test
    @DisplayName("Вызов метода удаления тренировки")
    void deleteTraining() {
        long id = 100L;

        trainingService.deleteTraining(id);

        verify(trainingDAO, times(1)).deleteTraining(id);
    }
}