package com.Y_LAB.homework.in.user_panel.auth_panel;

import com.Y_LAB.homework.audit.UserAuditResult;
import com.Y_LAB.homework.in.util.ConsoleReader;
import com.Y_LAB.homework.exception.auth.PasswordFormatException;
import com.Y_LAB.homework.exception.auth.PasswordsDoNotMatchException;
import com.Y_LAB.homework.exception.auth.UserAlreadyExistsException;
import com.Y_LAB.homework.exception.auth.UsernameFormatException;
import com.Y_LAB.homework.in.user_panel.HomePanel;
import com.Y_LAB.homework.service.user.UserService;
import com.Y_LAB.homework.service.user.UserServiceImpl;


/**
 * Класс для вывода панели по регистрации пользователя и взаимодействия с ней
 * @author Денис Попов
 * @version 2.0
 */
public class RegistrationPanel {

    /** Поле сервиса для взаимодействия с базой данных*/
    private static final UserService userService = new UserServiceImpl();

    private RegistrationPanel() {}

    /**
     * Метод для вывода информации по регистрации пользователя,
     * считывает введённый логин пользователя, вызывает метод
     * {@link RegistrationPanel#enterPasswordForRegistration()} для получения пароля.
     * Метод работает рекурсивно, в случае если пользователь с указанным логином уже существует или логин
     * не соответствует формату
     */
    public static void signUp() {
        String username;
        try {
            System.out.print("Введите логин: ");
            username = ConsoleReader.enterUsername();
            String password = enterPasswordForRegistration();
            userService.saveUser(username, password);
            userService.saveUserAudit(null, "Регистрация", UserAuditResult.SUCCESS);
            System.out.println("\nВы успешно создали аккаунт");
            HomePanel.printStartPage();
        } catch (UserAlreadyExistsException | UsernameFormatException ex) {
            System.out.println(ex.getMessage());
            signUp();
        }
    }

    /**
     * Метод для вывода информации по регистрации пользователя,
     * считывает введённый пароль пользователя с помощью {@link ConsoleReader#enterPassword()}, повторно
     * считывает введённый пароль пользователя для избежаний опечатки со стороны пользователя,
     * в случае несоответствия 2-х паролей или формата пароля выводится сообщение об ошибке и метод вызывается -
     * рекурсивно.
     * В случае успешного создания пользователя выводится сообщение, а так же вызывается метод домашней страницы
     * пользователя {@link HomePanel#printStartPage()}
     */
    private static String enterPasswordForRegistration() {
        String password;
        try {
            System.out.print("Введите пароль: ");
            password = ConsoleReader.enterPassword();
            System.out.print("Повторите пароль: ");
            ConsoleReader.repeatPassword(password);
        } catch (PasswordsDoNotMatchException | PasswordFormatException ex) {
            System.out.println(ex.getMessage());
            userService.saveUserAudit(null, "Регистрация", UserAuditResult.FAIL);
            return enterPasswordForRegistration();
        }
        return password;
    }
}
