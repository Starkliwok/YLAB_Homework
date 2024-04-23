package com.Y_LAB.homework.dao.user;


import com.Y_LAB.homework.entity.User;
import com.Y_LAB.homework.exception.auth.WrongUsernameAndPasswordException;

import java.util.List;

/**
 * Интерфейс описывает DAO, который содержит в себе методы по взаимодействию пользователями
 * @author Денис Попов
 * @version 1.0
 */
public interface UserDAO {

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
     * Метод для проверки существования пользователя по логину в базе данных
     * @param username логин пользователя
     * @return True - пользователь с таким логином существует. False - пользователя с таким логином не существует
     */
    boolean isUserExist(String username);

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
}
