package com.Y_LAB.homework.roles;

import com.Y_LAB.homework.exception.auth.UserDoesNotExistsException;
import com.Y_LAB.homework.exception.auth.WrongUsernameAndPasswordException;
import com.Y_LAB.homework.trainings.Training;

import java.util.*;
/**
 * Класс пользователя хранит в себе дневник тренировок и имеет методы для взаимодействия с ними
 * @author Денис Попов
 * @version 1.0
 */
public class User {

    /** Статическое поле количества пользователей, используется для инициализации поля {@link User#id}.*/
    private static int usersCount;

    /** Статическое поле которое содержит в себе логин в виде ключа и пароль в виде значения всех пользователей.*/
    private static final Map<String, String> allReservedUsernamesAndPasswords = new HashMap<>();

    /** Статическое поле которое содержит в себе всех пользователей.*/
    private static final Set<User> userSet = new HashSet<>();

    /** Поле уникального идентификатора, которое задается при создании объекта, это поле невозможно изменить.*/
    private final int id;

    /** Поле уникального логина пользователя, используется при входе в систему.*/
    private String username;

    /** Поле пароля пользователя, используется при входе в систему.*/
    private String password;

    /** Поле которое представляет собой дневник тренировок, где дата = ключ, список тренировок = значение.*/
    private final Map<Date, List<Training>> trainingHistory;

    /**
     * Конструктор, который принимает параметры и инициализирует поля класса
     * поле {@link User#id задается по количеству всех аккаунтов на данный момент}
     * инициализируется дневник тренировок {@link User#trainingHistory}
     * @param username логин
     * @param password пароль
     */
    public User(String username, String password) {
        id = ++usersCount;
        this.username = username;
        this.password = password;
        trainingHistory = new HashMap<>();
    }

    /**
     * Метод для добавления пользователя в {@link User#userSet}, а так же его логина и паролей в
     * {@link User#allReservedUsernamesAndPasswords}, этот метод используется при создании нового пользователя
     * @param user пользователь
     */
    public static void addUserToUserSet(User user) {
        userSet.add(user);
        allReservedUsernamesAndPasswords.put(user.getUsername(), user.getPassword());
    }

    /**
     * Метод для удаления пользователя из {@link User#userSet}, а так же его логина и паролей из
     * {@link User#allReservedUsernamesAndPasswords}
     * @param user пользователь
     */
    public static void deleteUserFromUserSet(User user) {
        userSet.remove(user);
        allReservedUsernamesAndPasswords.remove(user.getUsername());
    }

    /**
     * Метод для удаления пользователя из {@link User#userSet}, а так же его логина и паролей из
     * {@link User#allReservedUsernamesAndPasswords}
     * @param user пользователь
     */
    public static User getUserFromUserSet(User user) throws UserDoesNotExistsException {
        for (User element : userSet) {
            if (user.equals(element)) {
                user = element;
            }
        }
        if(user.getId() == 0) {
            throw new UserDoesNotExistsException("Пользователя не существует");
        }
        return user;
    }

    /**
     * Метод для получения пользователя из {@link User#userSet}
     * @param username логин пользователя
     * @param password пароль пользователя
     * @return возвращает пользователя,
     * @throws WrongUsernameAndPasswordException если пользователя с таким логином и паролем нет
     */
    public static User getUserFromUserSet(String username, String password) throws WrongUsernameAndPasswordException {
        for (User element : userSet) {
            if (username.equals(element.getUsername()) && password.equals(element.getPassword())) {
                return element;
            }
        }
            throw new WrongUsernameAndPasswordException("Неверный логин или пароль, повторите попытку");
    }

    /**
     * Метод для добавления тренировки в {@link User#trainingHistory}
     * @param date дата тренировки
     * @param training тренировка
     */
    public void addTrainingToHistory(Date date, Training training) {
        List<Training> trainingList;
        if (trainingHistory.containsKey(date)) {
            trainingList = trainingHistory.get(date);
        } else {
            trainingList = new ArrayList<>();
        }
        trainingList.add(training);
        trainingHistory.put(date, trainingList);
    }

    /**
     * Метод для удаления тренировки из {@link User#trainingHistory}
     * @param date дата тренировки
     * @param training тренировка
     */
    public void deleteTraining(Date date, Training training) {
        trainingHistory.get(date).remove(training);
    }

    /**
     * Метод для получения коллекции всех логинов и их паролей из {@link User#allReservedUsernamesAndPasswords}
     * @return все логины и пароли пользователей в виде ключа = значение
     */
    public static Map<String, String> getAllReservedUsernamesAndPasswords() {
        return allReservedUsernamesAndPasswords;
    }

    /**
     * Метод для получения коллекции всех логинов из {@link User#allReservedUsernamesAndPasswords}
     * @return все логины в виде множества
     */
    public static Set<String> getUsernameSet(){
        return allReservedUsernamesAndPasswords.keySet();
    }

    /**
     * Метод для получения коллекции всех пользователей из {@link User#userSet}
     * @return все пользователи в виде множества
     */
    public static Set<User> getUserSet() {
        return userSet;
    }

    /**
     * Метод для получения id пользователя
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Метод для получения логина пользователя
     * @return логин пользователя
     */
    public String getUsername() {
        return username;
    }

    /**
     * Метод для присваивания нового логина пользователю
     * @param username новый логин пользователя
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Метод для получения пароля пользователя
     * @return пароль пользователя
     */
    public String getPassword() {
        return password;
    }

    /**
     * Метод для установки пароля пользователя
     * @param password пароль пользователя
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Метод для получения дневника тренировок {@link User#trainingHistory}
     * @return дневник тренировок
     */
    public Map<Date, List<Training>> getTrainingHistory() {
        return trainingHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "\nПользователь" +
                "\nid = " + id +
                "\nИмя = '" + username + '\'' +
                "\nПароль = '" + password + '\'';
    }
}
