package com.Y_LAB.homework.service.training;

import com.Y_LAB.homework.entity.trainings.AdditionalData;
import com.Y_LAB.homework.entity.trainings.Training;

import java.util.Date;
import java.util.List;

/**
 * Интерфейс описывает сервис тренировок, который содержат в себе методы по взаимодействию с тренировками и дополнительной
 * информацией для тренировок объединённые с помощью {@link com.Y_LAB.homework.dao.training.TrainingDAO}
 * и {@link com.Y_LAB.homework.dao.training.AdditionalDataDAO}
 * @author Денис Попов
 * @version 1.0
 */
public interface TrainingService {

    /**
     * Метод для получения всей дополнительной информации тренировки из базы данных
     * @param trainingId идентификационный номер тренировки
     * @return Коллекция всей дополнительной информации, которая принадлежит одной тренировке
     */
    List<AdditionalData> getAllAdditionalData(long trainingId);

    /**
     * Метод для получения дополнительной информации тренировки из базы данных
     * @param name название дополнительной информации
     * @param value значение дополнительной информации
     * @param trainingId идентификационный номер тренировки
     * @return дополнительная информация, которая принадлежит тренировке
     */
    AdditionalData getAdditionalData(String name, String value, long trainingId);

    /**
     * Метод для сохранения дополнительной информации тренировки в базу данных
     * @param name название дополнительной информации
     * @param value значение дополнительной информации
     * @param trainingId идентификационный номер тренировки
     */
    void saveAdditionalData(String name, String value, long trainingId);

    /**
     * Метод для обновления дополнительной информации тренировки в базе данных
     * @param additionalData дополнительная информация
     */
    void updateAdditionalData(AdditionalData additionalData);

    /**
     * Метод для удаления дополнительной информации тренировки из базы данных
     * @param id номер дополнительной информации
     */
    void deleteAdditionalData(long id);

    /**
     * Метод для получения всех тренировок пользователя из базы данных
     * @param userId идентификационный номер пользователя
     * @return Коллекция всех тренировок, которые принадлежат одному пользователю
     */
    List<Training> getAllTrainings(long userId);

    /**
     * Метод для получения тренировки из базы данных
     * @param id идентификационный номер тренировки
     * @return Тренировка
     */
    Training getTraining(long id);

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
    long getTrainingId(String name, String type, Date date, int caloriesSpent, double durationInMinutes, long userId);

    /**
     * Метод для сохранения тренировки в базу данных
     * @param training тренировка
     */
    void saveTraining(Training training);

    /**
     * Метод для обновления тренировки в базе данных
     * @param training тренировка
     */
    void updateTraining(Training training);

    /**
     * Метод для удаления тренировки из базы данных
     * @param id идентификационный номер тренировки
     */
    void deleteTraining(long id);
}
