package com.Y_LAB.homework.service.training;

import com.Y_LAB.homework.dao.training.AdditionalDataDAO;
import com.Y_LAB.homework.dao.training.AdditionalDataDAOImpl;
import com.Y_LAB.homework.dao.training.TrainingDAO;
import com.Y_LAB.homework.dao.training.TrainingDAOImpl;
import com.Y_LAB.homework.entity.trainings.AdditionalData;
import com.Y_LAB.homework.entity.trainings.Training;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Класс реализовывает методы интерфейса {@link TrainingService}, который описывает методы по взаимодействию с тренировками
 * и дополнительной информацией для тренировок объединённые с помощью {@link com.Y_LAB.homework.dao.training.TrainingDAO}
 * и {@link com.Y_LAB.homework.dao.training.AdditionalDataDAO}
 * @author Денис Попов
 * @version 1.0
 */
@AllArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    /** Поле для взаимодействия с дополнительной информацией тренировок в базе данных*/
    private final AdditionalDataDAO additionalDataDAO;

    /** Поле для взаимодействия с тренировками в базе данных*/
    private final TrainingDAO trainingDAO;

    public TrainingServiceImpl() {
        additionalDataDAO = new AdditionalDataDAOImpl();
        trainingDAO = new TrainingDAOImpl();
    }

    /**
     * Метод вызывает {@link AdditionalDataDAO#getAllAdditionalData(long)} для получения всей дополнительной информации
     * тренировки из базы данных
     * @param trainingId идентификационный номер тренировки
     * @return Коллекция всей дополнительной информации, которая принадлежит одной тренировке
     */
    @Override
    public List<AdditionalData> getAllAdditionalData(long trainingId) {
        return additionalDataDAO.getAllAdditionalData(trainingId);
    }

    /**
     * Метод вызывает {@link AdditionalDataDAO#getAdditionalData(String, String, long)} для получения дополнительной
     * информации тренировки из базы данных
     * @param name название дополнительной информации
     * @param value значение дополнительной информации
     * @param trainingId идентификационный номер тренировки
     * @return дополнительная информация, которая принадлежит тренировке
     */
    @Override
    public AdditionalData getAdditionalData(String name, String value, long trainingId) {
        return additionalDataDAO.getAdditionalData(name, value, trainingId);
    }

    /**
     * Метод вызывает {@link AdditionalDataDAO#saveAdditionalData(String, String, long)} для сохранения дополнительной
     * информации тренировки в базу данных
     * @param name название дополнительной информации
     * @param value значение дополнительной информации
     * @param trainingId идентификационный номер тренировки
     */
    @Override
    public void saveAdditionalData(String name, String value, long trainingId) {
        additionalDataDAO.saveAdditionalData(name, value, trainingId);
    }

    /**
     * Метод вызывает {@link AdditionalDataDAO#updateAdditionalData(AdditionalData)} для обновления дополнительной
     * информации тренировки в базе данных
     * @param additionalData дополнительная информация
     */
    @Override
    public void updateAdditionalData(AdditionalData additionalData) {
        additionalDataDAO.updateAdditionalData(additionalData);
    }

    /**
     * Метод вызывает {@link AdditionalDataDAO#deleteAdditionalData(long)} для удаления дополнительной
     * информации тренировки из базы данных
     * @param id номер дополнительной информации
     */
    @Override
    public void deleteAdditionalData(long id) {
        additionalDataDAO.deleteAdditionalData(id);
    }

    /**
     * Метод вызывает {@link TrainingDAO#getAllTrainings(long)} для получения всех тренировок пользователя
     * из базы данных
     * @param userId идентификационный номер пользователя
     * @return Коллекция всех тренировок, которые принадлежат одному пользователю
     */
    @Override
    public List<Training> getAllTrainings(long userId) {
        return trainingDAO.getAllTrainings(userId);
    }

    /**
     * Метод вызывает {@link TrainingDAO#getTraining(long)} для получения тренировки из базы данных
     * @param id идентификационный номер тренировки
     * @return Тренировка
     */
    @Override
    public Training getTraining(long id) {
        return trainingDAO.getTraining(id);
    }

    /**
     * Метод для получения идентификационного номера тренировки из базы данных
     * @param name имя тренировки
     * @param type тип тренировки
     * @param date дата тренировки
     * @param caloriesSpent количество потраченных калорий
     * @param durationInMinutes длительность тренировки в минутах
     * @param userId идентификационный номер владельца тренировки
     * @return Тренировка
     */
    @Override
    public long getTrainingId(String name, String type, Date date, int caloriesSpent, double durationInMinutes,
                              long userId) {
        return trainingDAO.getTrainingId(name, type, date, caloriesSpent, durationInMinutes, userId);
    }

    /**
     * Метод вызывает {@link TrainingDAO#saveTraining(Training)} для сохранения тренировки в базу данных
     * @param training тренировка
     */
    @Override
    public void saveTraining(Training training) {
        trainingDAO.saveTraining(training);
    }

    /**
     * Метод вызывает {@link TrainingDAO#updateTraining(Training)} для обновления тренировки в базе данных
     * @param training тренировка
     */
    @Override
    public void updateTraining(Training training) {
        trainingDAO.updateTraining(training);
    }

    /**
     * Метод вызывает {@link TrainingDAO#deleteTraining(long)} для удаления тренировки из базы данных
     * @param id идентификационный номер тренировки
     */
    @Override
    public void deleteTraining(long id) {
        trainingDAO.deleteTraining(id);
    }
}
