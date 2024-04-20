package com.Y_LAB.homework.entity;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс для аудирования действий пользователя
 *
 * @param id              Поле идентификационного номера
 *                        -- GETTER --
 *                        Метод для получения идентификационного номера
 * @param dateTime        Поле даты
 *                        -- GETTER --
 *                        Метод для получения даты
 * @param userId          Поле идентификационного номера пользователя
 *                        -- GETTER --
 *                        Метод для получения идентификационного номера пользователя
 * @param action          Поле действия пользователя
 *                        -- GETTER --
 *                        Метод для получения действий пользователя
 * @param userAuditResult Поле результата действия пользователя
 *                        -- GETTER --
 *                        Метод для получения результата действий пользователя
 * @author Денис Попов
 * @version 2.0
 */
public record UserAudit(long id, LocalDateTime dateTime, Long userId, String action, String userAuditResult) {
    @Override
    public String toString() {
        return "id = " + id + ", время = " + dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")) +
                ", id пользователя = " + userId +
                ", действие = '" + action + '\'' +
                ", результат = '" + userAuditResult + '\'';
    }
}

