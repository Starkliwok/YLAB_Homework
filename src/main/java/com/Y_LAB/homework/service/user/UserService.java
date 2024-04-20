package com.Y_LAB.homework.service.user;

import com.Y_LAB.homework.audit.UserAuditResult;
import com.Y_LAB.homework.entity.User;
import com.Y_LAB.homework.entity.UserAudit;
import com.Y_LAB.homework.exception.auth.WrongUsernameAndPasswordException;

import java.util.List;

/**
 * Интерфейс описывает сервис пользователя, который содержит в себе методы по взаимодействию с пользователями и их аудита
 * объединённые с помощью {@link com.Y_LAB.homework.dao.user.UserDAO}
 * и {@link com.Y_LAB.homework.dao.audit.UserAuditDAO}
 * @author Денис Попов
 * @version 1.0
 */
public interface UserService {

    /**
     * Метод для получения всех пользователей из базы данных
     * @return Коллекция всех пользователей
     */
    List<User> getAllUsers();

    /**
     * Метод для получения пользователя из базы данных
     * @param username логин пользователя
     * @param password пароль пользователя
     * @return пользователь
     * @throws WrongUsernameAndPasswordException когда пользователя с таким логином и паролем не существует
     */
    User getUser(String username, String password) throws WrongUsernameAndPasswordException;

    /**
     * Метод для сохранения пользователя в базу данных
     * @param username логин пользователя
     * @param password пароль пользователя
     */
    void saveUser(String username, String password);

    /**
     * Метод для обновления пользователя в базе данных
     * @param user пользователь
     */
    void updateUser(User user);

    /**
     * Метод для удаления пользователя из базы данных
     * @param id идентификационный номер пользователя
     */
    void deleteUser(long id);

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
