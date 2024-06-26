package com.Y_LAB.homework.entity.trainings;

import lombok.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс описания тренировки, помимо обычных методов содержит дополнительную информацию, которую пользователь может
 * заполнять по желанию
 * @author Денис Попов
 * @version 2.0
 */
@Data
@NoArgsConstructor
public class Training {

    /** Поле идентификационного номера*/
    private long id;

    /** Поле названия тренировки*/
    private String name;

    /** Поле названия типа тренировки*/
    private String type;

    /** Поле даты тренировки*/
    private Date date;

    /** Поле количества потраченных калорий тренировки*/
    private int caloriesSpent;

    /** Поле длительности тренировки в минутах*/
    private double durationInMinutes;

    /** Поле идентификационного номера владельца тренировки*/
    private long userId;

    /** Поле дополнительная информация для тренировки в виде коллекции ключ=значение*/
    private Map<String, String> additionalDataMap = new HashMap<>();

    /** Конструктор инициализирует все поля класса и коллекцию дополнительной информации
     * {@link Training#additionalDataMap}
     * @param id идентификационный номер
     * @param name название тренировки
     * @param type тип тренировки
     * @param date дата тренировки
     * @param caloriesSpent количество потраченных калорий тренировки
     * @param durationInMinutes длительность тренировки в минутах
     * @param userId идентификационный номер владельца тренировки*/
    public Training(long id, String name, String type, Date date, int caloriesSpent
            , double durationInMinutes, long userId) {
        this.id  = id;
        this.name = name;
        this.type = type;
        this.date = date;
        this.caloriesSpent = caloriesSpent;
        this.durationInMinutes = durationInMinutes;
        this.userId = userId;
        additionalDataMap = new HashMap<>();
    }

    @Override
    public String toString() {
        return "\n1.Тип тренировки = " + type +
                ", \n2.Название = '" + name + '\'' +
                ", \n3.Калорий сожжено = " + caloriesSpent +
                ", \n4.Длительность в минутах = " + durationInMinutes +
                ", \n5.Дополнительная информация:" + additionalDataMap
                .toString().replaceAll("^\\{|}$", "\n");
    }
}
