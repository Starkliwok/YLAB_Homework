package com.Y_LAB.homework.dao.audit;


import com.Y_LAB.homework.audit.UserAuditResult;
import com.Y_LAB.homework.entity.UserAudit;

import java.util.List;

/**
 * Интерфейс описывает DAO, который содержит в себе методы по взаимодействию с аудитами пользователей
 * @author Денис Попов
 * @version 1.0
 */
public interface UserAuditDAO {

    /**
     * Метод для получения всех аудитов пользователей из базы данных
     * @return Коллекция всех аудитов пользователей
     */
    List<UserAudit> getAllUserAudits();

    /**
     * Метод для получения аудита пользователя из базы данных
     * @param id идентификационный номер аудита
     * @return аудитов пользователя
     */
    UserAudit getUserAudit(long id);

    /**
     * Метод для получения аудита пользователя из базы данных
     * @param userId идентификационный номер пользователя
     * @param action действия пользователя
     * @param userAuditResult результат действий пользователя
     */
    void saveUserAudit(Long userId, String action, UserAuditResult userAuditResult);
}
