package com.Y_LAB.homework.exception.training;

/**
 * Класс наследуется от {@link Exception} и описывает нарушение минимальной длины значения {@link String}
 * при вводе данных пользователем
 * @author Денис Попов
 * @version 1.0
 */
public class TrainingFieldMinimumLengthException extends Exception {
    public TrainingFieldMinimumLengthException(String message){
        super(message);
    }
}
