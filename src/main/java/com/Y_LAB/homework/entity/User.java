package com.Y_LAB.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * Класс пользователя хранит в себе дневник тренировок и имеет методы для взаимодействия с ними
 * @author Денис Попов
 * @version 2.0
 */
@Data
@AllArgsConstructor
public class User {

    /** Поле уникального идентификатора, которое задается при создании объекта, это поле невозможно изменить.*/
    private final long id;

    /** Поле уникального логина пользователя, используется при входе в систему.*/
    private String username;

    /** Поле пароля пользователя, используется при входе в систему.*/
    private String password;

    @Override
    public String toString() {
        return "\nПользователь" +
                "\nid = " + id +
                "\nИмя = '" + username + '\'' +
                "\nПароль = '" + password + '\'';
    }
}
