package com.Y_LAB.homework.entity.trainings;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс дополнительной информации для тренировки
 * @author Денис Попов
 * @version 2.0
 */
@Getter
@EqualsAndHashCode
public class AdditionalData {

    /** Поле значения с идентификационным номером
     * -- GETTER --
     *  Метод для получения значения параметра
     */
    private final long id;

    /** Поле названия дополнительной информации
     * -- GETTER --
     *  Метод для получения названия параметра
     * -- SETTER --
     *  Метод для присваивания названия параметра
     */
    @Setter
    private String name;

    /** Поле значения дополнительной информации
     * -- GETTER --
     *  Метод для получения значения параметра
     * -- SETTER --
     *  Метод для присваивания значения параметра
     */
    @Setter
    private String value;

    /** Поле значения с идентификационным номером тренировки
     * -- GETTER --
     *  Метод для получения значения параметра
     */
    private final long trainingId;

    /**
     * Конструктор, который принимает параметры и инициализирует поля класса
     * @param name название параметра
     * @param value значение параметра
     */
    public AdditionalData(long id, String name, String value, long trainingId) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.trainingId = trainingId;
    }

    @Override
    public String toString() {
        return "\n" + name +
                ": " + value;
    }
}
