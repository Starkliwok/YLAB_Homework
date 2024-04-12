package com.Y_LAB.homework.roles;

import java.util.Map;
import java.util.Set;

/**
 * Класс администратора обладает правами как обычного {@link User}, так и правами по удалению, изменению данных других
 * пользователей, связанных с их данными от аккаунта или дневника тренировок
 * @author Денис Попов
 * @version 1.0
 */
public class Admin extends User {

    /**
     * Конструктор, который принимает параметры и передает их в родительский класс
     * @param username логин
     * @param password пароль
     */
    public Admin(String username, String password) {
        super(username, password);
    }

    /**
     * Метод для изменения логина пользователя
     * метод получает список всех зарезервированных логинов и паролей, списка пользователей и обновляет в них информацию
     * по логину
     * @param user пользователь
     * @param newName новое имя
     */
    public void updateUserName(User user, String newName) {
        Set<User> userSet = User.getUserSet();
        Map<String, String> allReversedUsername = User.getAllReservedUsernamesAndPasswords();

        allReversedUsername.remove(user.getUsername());
        user.setUsername(newName);
        allReversedUsername.put(newName, user.getPassword());
        userSet.add(user);
    }

    /**
     * Метод для изменения пароля пользователя
     * метод получает список всех зарезервированных логинов и паролей, список пользователей и обновляет в
     * них информацию по паролю
     * @param user пользователь
     * @param newPassword новый пароль
     */
    public void updateUserPassword(User user, String newPassword) {
        Set<User> userSet = User.getUserSet();
        Map<String, String> allReversedUsername = User.getAllReservedUsernamesAndPasswords();

        user.setPassword(newPassword);
        allReversedUsername.put(user.getUsername(), newPassword);
        userSet.add(user);
    }

    /**
     * Метод для удаления пользователя из списков пользователей и списка всех зарезервированных логинов и паролей
     * метод вызывает {@link User#deleteUserFromUserSet(User)}
     * @param user пользователь
     */
    public void deleteUser(User user) {
        User.deleteUserFromUserSet(user);
    }
}
