package com.Y_LAB.homework.exception.auth;

/**
 * Класс наследуется от {@link Exception} и которое описывает случай, когда указанные логин и пароль не соответствуют существующему пользователю
 * @author Денис Попов
 * @version 1.0
 */
public class UserDoesNotExistsException extends Exception {
    public UserDoesNotExistsException(String message) {
        super(message);
    }
}
