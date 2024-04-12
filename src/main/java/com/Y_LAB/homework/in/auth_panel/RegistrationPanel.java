package com.Y_LAB.homework.in.auth_panel;

import com.Y_LAB.homework.audit.UserAudit;
import com.Y_LAB.homework.audit.UserAuditResult;
import com.Y_LAB.homework.in.util.ConsoleReader;
import com.Y_LAB.homework.roles.User;
import com.Y_LAB.homework.exception.auth.PasswordFormatException;
import com.Y_LAB.homework.exception.auth.PasswordsDoNotMatchException;
import com.Y_LAB.homework.exception.auth.UserAlreadyExistsException;
import com.Y_LAB.homework.exception.auth.UsernameFormatException;
import com.Y_LAB.homework.in.user_panel.HomePanel;

import java.time.LocalDateTime;

/**
 * Класс для вывода панели по регистрации пользователя и взаимодействия с ней
 * @author Денис Попов
 * @version 1.0
 */
public class RegistrationPanel {

    private RegistrationPanel() {}

    /**
     * Метод для вывода информации по регистрации пользователя,
     * считывает введённый логин пользователя, вызывает метод
     * {@link RegistrationPanel#printPasswordRegistrationPage(String)} с параметром логина.
     * Метод работает рекурсивно, в случае если пользователь с указанным логином уже существует или логин
     * не соответствует формату
     */
    public static void printRegistrationPage() {
        String username;
        try {
            System.out.print("Введите логин: ");
            username = ConsoleReader.enterUsername();
            printPasswordRegistrationPage(username);
        } catch (UserAlreadyExistsException | UsernameFormatException ex) {
            System.out.println(ex.getMessage());
            printRegistrationPage();
        }
    }

    /**
     * Метод для вывода информации по регистрации пользователя,
     * считывает введённый пароль пользователя с помощью {@link ConsoleReader#enterPassword()}, повторно
     * считывает введённый пароль пользователя для избежаний опечатки со стороны пользователя,
     * в случае несоответствия 2-х паролей или формата пароля выводится сообщение об ошибке и метод вызывается -
     * рекурсивно. Вызывает метод {@link RegistrationPanel#createNewUser(String, String)} с параметрами логина и пароля.
     * В случае успешного создания пользователя выводится сообщение, а так же вызывается метод домашней страницы
     * пользователя {@link HomePanel#printStartPage()}
     * @param username логин будущего пользователя
     */
    private static void printPasswordRegistrationPage(String username) {
        String password;
        try {
            System.out.print("Введите пароль: ");
            password = ConsoleReader.enterPassword();
            System.out.print("Повторите пароль: ");
            ConsoleReader.repeatPassword(password);
            createNewUser(username, password);
        } catch (PasswordsDoNotMatchException | PasswordFormatException ex) {
            System.out.println(ex.getMessage());
            printPasswordRegistrationPage(username);
            UserAudit.addLog("Registration", LocalDateTime.now(), username, UserAuditResult.FAIL);
        }
        UserAudit.addLog("Registration", LocalDateTime.now(), username, UserAuditResult.SUCCESS);
        System.out.println("\nВы успешно создали аккаунт");
        HomePanel.printStartPage();
    }

    /**
     * Метод для создания нового пользователя,
     * создает нового пользователя на основе полученных параметров, а так же добавляет его в коллекцию со всеми
     * пользователями приложения
     * @param username логин будущего пользователя
     * @param password пароль будущего пользователя
     */
    public static void createNewUser(String username, String password) {
        User user = new User(username, password);
        User.addUserToUserSet(user);
    }
}
