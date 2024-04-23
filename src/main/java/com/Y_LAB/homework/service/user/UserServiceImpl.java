package com.Y_LAB.homework.service.user;

import com.Y_LAB.homework.audit.UserAuditResult;
import com.Y_LAB.homework.dao.audit.UserAuditDAO;
import com.Y_LAB.homework.dao.audit.UserAuditDAOImpl;
import com.Y_LAB.homework.dao.user.UserDAO;
import com.Y_LAB.homework.dao.user.UserDAOImpl;
import com.Y_LAB.homework.entity.User;
import com.Y_LAB.homework.entity.UserAudit;
import com.Y_LAB.homework.exception.auth.WrongUsernameAndPasswordException;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Класс реализовывает методы интерфейса {@link UserService}, который описывает сервис пользователя,
 * содержит в себе методы по взаимодействию с пользователями и их аудита
 * объединённые с помощью {@link com.Y_LAB.homework.dao.user.UserDAO}
 * и {@link com.Y_LAB.homework.dao.audit.UserAuditDAO}
 * @author Денис Попов
 * @version 1.0
 */
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    /** Поле для взаимодействия с пользователями в базе данных*/
    private final UserDAO userDAO;

    /** Поле для взаимодействия с аудитом пользователей в базе данных*/
    private final UserAuditDAO userAuditDAO;

    public UserServiceImpl() {
        userDAO = new UserDAOImpl();
        userAuditDAO = new UserAuditDAOImpl();
    }

    /**
     * Метод вызывает {@link UserDAO#getAllUsers()} для получения всех пользователей из базы данных
     * @return Коллекция всех пользователей
     */
    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    /**
     * Метод вызывает {@link UserDAO#getUser(String, String)} для получения пользователя из базы данных
     * @param username логин пользователя
     * @param password пароль пользователя
     * @return пользователь
     * @throws WrongUsernameAndPasswordException когда пользователя с таким логином и паролем не существует
     */
    @Override
    public User getUser(String username, String password) throws WrongUsernameAndPasswordException {
        return userDAO.getUser(username, password);
    }

    /**
     * Метод вызывает {@link UserDAO#saveUser(String, String)} для сохранения пользователя в базу данных
     * @param username логин пользователя
     * @param password пароль пользователя
     */
    @Override
    public void saveUser(String username, String password) {
        userDAO.saveUser(username, password);
    }

    /**
     * Метод вызывает {@link UserDAO#updateUser(User)} для обновления пользователя в базе данных
     * @param user пользователь
     */
    @Override
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    /**
     * Метод вызывает {@link UserDAO#deleteUser(long)} для удаления пользователя из базы данных
     * @param id идентификационный номер пользователя
     */
    @Override
    public void deleteUser(long id) {
        userDAO.deleteUser(id);
    }

    /**
     * Метод вызывает {@link UserAuditDAO#getAllUserAudits} для получения всех аудитов пользователей из базы данных
     * @return Коллекция всех аудитов пользователей
     */
    @Override
    public List<UserAudit> getAllUserAudits() {
        return userAuditDAO.getAllUserAudits();
    }

    /**
     * Метод вызывает {@link UserAuditDAO#getUserAudit(long)} для получения аудита пользователя из базы данных
     * @param id идентификационный номер аудита
     * @return аудитов пользователя
     */
    @Override
    public UserAudit getUserAudit(long id) {
        return userAuditDAO.getUserAudit(id);
    }

    /**
     * Метод для проверки существования пользователя по логину в базе данных
     * @param username логин пользователя
     * @return True - пользователь с таким логином существует. False - пользователя с таким логином не существует
     */
    @Override
    public boolean isUserExist(String username) {
        return userDAO.isUserExist(username);
    }

    /**
     * Метод вызывает {@link UserAuditDAO#saveUserAudit(Long, String, UserAuditResult)} для сохранения аудита
     * пользователя из базу данных
     * @param userId идентификационный номер пользователя
     * @param action действия пользователя
     * @param userAuditResult результат действий пользователя
     */
    @Override
    public void saveUserAudit(Long userId, String action, UserAuditResult userAuditResult) {
        userAuditDAO.saveUserAudit(userId, action, userAuditResult);
    }
}
