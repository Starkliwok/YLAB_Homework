package com.Y_LAB.homework.dao.training;


import com.Y_LAB.homework.entity.trainings.Training;

import java.util.List;

/**
 * Интерфейс описывает DAO, который содержит в себе методы по взаимодействию с тренировками
 * @author Денис Попов
 * @version 1.0
 */
public interface TrainingDAO {

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
