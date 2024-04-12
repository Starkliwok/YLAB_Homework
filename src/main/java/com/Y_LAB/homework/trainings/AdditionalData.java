package com.Y_LAB.homework.trainings;

import java.util.Objects;

/**
 * Класс дополнительной информации для тренировки
 * @author Денис Попов
 * @version 1.0
 */
public class AdditionalData {

    /** Поле названия дополнительной информация*/
    private String name;

    /** Поле значения дополнительной информация*/
    private String value;

    /**
     * Конструктор, который принимает параметры и инициализирует поля класса
     * @param name название параметра
     * @param value значение параметра
     */
    public AdditionalData(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Метод для получения названия параметра
     * @return название параметра дополнительной информации
     */
    public String getName() {
        return name;
    }

    /**
     * Метод для присваивания названия параметра
     * @param name название параметра
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Метод для получения значения параметра
     * @return значение параметра дополнительной информации
     */
    public String getValue() {
        return value;
    }

    /**
     * Метод для присваивания значения параметра
     * @param value значение параметра
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "\n" + name +
                ": " + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdditionalData data)) return false;
        return Objects.equals(name, data.name) && Objects.equals(value, data.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
