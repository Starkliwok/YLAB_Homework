package com.Y_LAB.homework.in.util;

import com.Y_LAB.homework.dao.user.UserDAO;
import com.Y_LAB.homework.dao.user.UserDAOImpl;
import com.Y_LAB.homework.exception.auth.PasswordFormatException;
import com.Y_LAB.homework.exception.auth.PasswordsDoNotMatchException;
import com.Y_LAB.homework.exception.auth.UserAlreadyExistsException;
import com.Y_LAB.homework.exception.auth.UsernameFormatException;
import com.Y_LAB.homework.exception.training.TrainingFieldMinimumLengthException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Класс для считывания введённых данных пользователем
 * @author Денис Попов
 * @version 2.0
 */
public class ConsoleReader {

    /** Поле сканера, предназначенное для считывания введённых данных*/
    private static final Scanner scanner = new Scanner(System.in);

    private ConsoleReader() {}

    /**
     * Метод для считывания целочисленного значения, которое предназначено для выбора действий на различных панелях
     * @return пользовательский ввод, в случае ошибок преобразования возвращает -1
     */
    public static int PageChoose() {
        int choose;
        try {
            choose = scanner.nextInt();
        } catch (InputMismatchException exception) {
            choose = -1;
            scanner.next();
        }
        return choose;
    }

    /**
     * Метод для считывания даты
     * @return пользовательский ввод, в случае ошибок преобразования выводит сообщение об ошибке
     * и рекурсивно вызывает себя
     */
    public static Date enterDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
        Date date;
        try {
            System.out.print("Введите дату в формате \"ДД-ММ-ГГГГ\": ");
            date = java.sql.Date.valueOf(LocalDate.parse(scanner.next(), dateTimeFormatter));
        } catch (DateTimeParseException ex) {
            System.out.println("\nДата не соответствует формату");

            return enterDate();
        }
        return date;
    }

    /**
     * Метод для считывания строки, принимает значение минимальной длины считываемой строки
     * @return пользовательский ввод, в случае ошибок преобразования выводит сообщение об ошибке
     * и рекурсивно вызывает себя
     * @param lengthConstraint минимальная длина строки
     */
    public static String enterStringValue(int lengthConstraint) {
        String value;
        try {
            value = scanner.next();
            if(value.length() < lengthConstraint){
                throw new TrainingFieldMinimumLengthException("Минимальная длина значения "
                        + lengthConstraint + " символа, повторите попытку");
            }
        } catch (TrainingFieldMinimumLengthException e) {
            System.out.println(e.getMessage());
            return enterStringValue(lengthConstraint);
        }
        return value;
    }

    /**
     * Метод для считывания целочисленного значения
     * @return пользовательский ввод, в случае ошибок преобразования выводит сообщение об ошибке
     * и рекурсивно вызывает себя
     */
    public static int enterIntValue() {
        int value;
        try {
            value = scanner.nextInt();
        } catch (InputMismatchException ex) {
            System.out.println("Повторите попытку, введите целое число");
            scanner.next();
            return enterIntValue();
        }
        return value;
    }

    /**
     * Метод для считывания числа с плавающей точкой
     * @return пользовательский ввод, в случае ошибок преобразования выводит сообщение об ошибке
     * и рекурсивно вызывает себя
     */
    public static double enterDoubleValue() {
        int value;
        try {
            value = scanner.nextInt();
        } catch (InputMismatchException ex) {
            System.out.println("Повторите попытку, введите целое или дробное число");
            scanner.next();
            return enterDoubleValue();
        }
        return value;
    }

    /**
     * Метод для считывания логина пользователя
     * @throws UsernameFormatException в случае если длина логина меньше 2-х символов
     * @throws UserAlreadyExistsException в случае если пользователь с таким именем уже существует
     * @return пользовательский ввод
     */
    public static String enterUsername() throws UsernameFormatException, UserAlreadyExistsException {
        UserDAO userDAO = new UserDAOImpl();

        String username = scanner.next();
        if(username.length() < 2) {
            throw new UsernameFormatException("Минимальная длина имени - 2 символа, повторите попытку\n");
        } else if(!userDAO.getAllUsers().stream().filter
                (user1 -> user1.getUsername().equals(username)).toList().isEmpty()) {
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует, повторите попытку\n");
        }
        return username;
    }

    /**
     * Метод для считывания пароля пользователя
     * @throws PasswordFormatException в случае если длина пароля меньше 6-х символов
     * @return пользовательский ввод
     */
    public static String enterPassword() throws PasswordFormatException {
        String password = scanner.next();
        if(password.length() < 6) {
            throw new PasswordFormatException("Минимальная длина пароля - 6 символов, повторите попытку\n");
        }
        return password;
    }

    /**
     * Метод для повторного считывания пароля пользователя
     * @throws PasswordsDoNotMatchException в случае если пароли не совпадают
     * @param password пароль, который пользователь уже ввел до этого
     */
    public static void repeatPassword(String password) throws PasswordsDoNotMatchException {
        if(!password.equals(scanner.next())) {
            throw new PasswordsDoNotMatchException("Пароли не совпадают, повторите попытку\n");
        }
    }
}
