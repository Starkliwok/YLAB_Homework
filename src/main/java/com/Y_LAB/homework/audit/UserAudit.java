package com.Y_LAB.homework.audit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс для аудирования действий пользователя, все логи записываются в поле <b>log</b>.
 * @author Денис Попов
 * @version 1.0
 */
public class UserAudit {

    /** Поле для хранения логов.*/
    private static final StringBuilder log = new StringBuilder();

    private UserAudit() {}

    /**
     * Метод добавления новой записи в {@link UserAudit#log}
     * @param action Описывает действия пользователя
     * @param dateTime содержит информацию о дате и времени
     * @param username уникальный логин пользователя
     * @param userAuditResult результат действий пользователя {@link UserAuditResult}
     */
    public static void addLog(String action, LocalDateTime dateTime, String username, UserAuditResult userAuditResult) {
        log.append(action)
                .append(" ")
                .append(dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")))
                .append(" username=").append(username)
                .append(" [")
                .append(userAuditResult)
                .append("]\n");
    }

    /**
     * Метод получения значения поля {@link UserAudit#log}
     * @return возвращает содержание лога.*/
    public static StringBuilder getLog() {
        return log;
    }
}

