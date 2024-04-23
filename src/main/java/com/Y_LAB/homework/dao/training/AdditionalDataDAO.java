package com.Y_LAB.homework.dao.training;

import com.Y_LAB.homework.entity.trainings.AdditionalData;

import java.util.List;

/**
 * Интерфейс описывает DAO, который содержит в себе методы по взаимодействию с дополнительной информацией тренировок
 * @author Денис Попов
 * @version 1.0
 */
public interface AdditionalDataDAO {

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
}
