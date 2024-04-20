package com.Y_LAB.homework.entity;

import lombok.Data;
/**
 * Класс пользователя хранит в себе дневник тренировок и имеет методы для взаимодействия с ними
 * @author Денис Попов
 * @version 2.0
 */
@Data
public class User {

    /** Поле уникального идентификатора, которое задается при создании объекта, это поле невозможно изменить.
     * -- GETTER --
     *  Метод для получения id пользователя
     * -- SETTER --
     *  Метод для присваивания id пользователю
     */
    private final long id;

    /** Поле уникального логина пользователя, используется при входе в систему.
     * -- GETTER --
     *  Метод для получения логина пользователя
     * -- SETTER --
     *  Метод для присваивания нового логина пользователю
     */
    private String username;

    /** Поле пароля пользователя, используется при входе в систему.
     * -- GETTER --
     *  Метод для получения пароля пользователя
     * -- SETTER --
     *  Метод для установки пароля пользователя
     */
    private String password;

    /**
     * Конструктор, который принимает параметры и инициализирует поля класса
     * @param username логин
     * @param password пароль
     */
    public User(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }


    @Override
    public String toString() {
        return "\nПользователь" +
                "\nid = " + id +
                "\nИмя = '" + username + '\'' +
                "\nПароль = '" + password + '\'';
    }
}
