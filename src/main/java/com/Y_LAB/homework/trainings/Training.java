package com.Y_LAB.homework.trainings;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс описания тренировки, помимо обычных методов содержит дополнительную информацию, которую пользователь может
 * заполнять по желанию
 * @author Денис Попов
 * @version 1.0
 */
public class Training {
    /** Поле названия тренировки*/
    private String name;

    /** Поле названия типа тренировки*/
    private String type;

    /** Поле количество потраченных калорий тренировки*/
    private int caloriesSpent;

    /** Поле длительности тренировки в минутах*/
    private double durationInMinutes;

    /** Поле дополнительная информация для тренировки в виде коллекции ключ=значение*/
    private Map<String, String> additionalDataMap;

    /** Конструктор инициализирует коллекцию дополнительной информации {@link Training#additionalDataMap}*/
    public Training() {
        additionalDataMap = new HashMap<>();
    }

    /**
     * Метод для добавления дополнительной информации {@link AdditionalData} в коллекцию дополнительной информации
     * {@link Training#additionalDataMap}
     * @param additionalData дополнительная информация
     */
    public void addAdditionalData(AdditionalData additionalData) {
        additionalDataMap.put(additionalData.getName(), additionalData.getValue());
    }

    /**
     * Метод для получения названия тренировки
     * @return название тренировки
     */
    public String getName() {
        return name;
    }

    /**
     * Метод для присваивания названия тренировки
     * @param name название тренировки
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Метод для получения количества потраченных калорий
     * @return количество потраченных калорий
     */
    public int getCaloriesSpent() {
        return caloriesSpent;
    }

    /**
     * Метод для присваивания количества потраченных калорий
     * @param caloriesSpent количество потраченных калорий
     */
    public void setCaloriesSpent(int caloriesSpent) {
        this.caloriesSpent = caloriesSpent;
    }

    /**
     * Метод для получения количества потраченных калорий
     * @return количество потраченных калорий
     */
    public double getDurationInMinutes() {
        return durationInMinutes;
    }

    /**
     * Метод для присваивания длительности в минутах
     * @param durationInMinutes длительность в минутах
     */
    public void setDurationInMinutes(double durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    /**
     * Метод для получения коллекции дополнительной информации
     * @return коллекция дополнительной информации
     */
    public Map<String, String> getAdditionalDataMap() {
        return additionalDataMap;
    }

    /**
     * Метод для присваивания коллекции дополнительной информации
     * @param additionalDataMap коллекция дополнительной информации
     */
    public void setAdditionalDataMap(Map<String, String> additionalDataMap) {
        this.additionalDataMap = additionalDataMap;
    }

    /**
     * Метод для получения типа тренировки
     * @return тип тренировки
     */
    public String getType() {
        return type;
    }

    /**
     * Метод для присваивания типа тренировке
     * @param type тип тренировки
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "\n" + type + " тренировка" +
                ", \n1.Название = '" + name + '\'' +
                ", \n2.Калорий сожжено = " + caloriesSpent +
                ", \n3.Длительность в минутах = " + durationInMinutes +
                ", \n4.Дополнительная информация:" + additionalDataMap
                .toString().replaceAll("^\\{|}$", "\n");
    }
}
