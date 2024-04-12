package com.Y_LAB.homework.in.auth_panel;

import com.Y_LAB.homework.audit.UserAudit;
import com.Y_LAB.homework.audit.UserAuditResult;
import com.Y_LAB.homework.in.user_panel.AdminPanel;
import com.Y_LAB.homework.in.user_panel.HomePanel;
import com.Y_LAB.homework.in.util.ConsoleReader;
import com.Y_LAB.homework.roles.Admin;
import com.Y_LAB.homework.roles.User;
import com.Y_LAB.homework.exception.auth.WrongUsernameAndPasswordException;
import com.Y_LAB.homework.in.user_panel.UserPanel;

import java.time.LocalDateTime;

/**
 * Класс для вывода панели по авторизации пользователя и взаимодействия с ней
 * @author Денис Попов
 * @version 1.0
 */
public class AuthorizationPanel {

    private AuthorizationPanel() {}

    /**
     * Метод для вывода информации по авторизации пользователя,
     * считывает введённые данные пользователя с помощью метода <br>
     * {@link ConsoleReader#enterStringValue(int)},
     * в зависимости от типа полученного пользователя из метода <br>
     * {@link AuthorizationPanel#logIn(String, String)} вызывает соответствующий класс для пользователя - <br>
     * {@link AdminPanel#printAdminPage(Admin)}<br> или <br>{@link UserPanel#printUserPage(User)}<br>
     * для вывода информации по возможным действиям пользователя,
     * в случае когда пользователь по указанным логину и паролю не существует - вызывается домашняя страница
     * {@link HomePanel#printStartPage()}
     */
    public static void printLogInPage() {
        System.out.print("Введите логин: ");
        String username = ConsoleReader.enterStringValue(0);
        System.out.print("Введите пароль: ");
        String password = ConsoleReader.enterStringValue(0);
        User user;
        try {
            user = logIn(username, password);
            System.out.println("Вы успешно авторизовались как " + user.getUsername() + "\n");
            UserAudit.addLog("Authorization", LocalDateTime.now(), user.getUsername(), UserAuditResult.SUCCESS);
            if(user instanceof Admin){
                AdminPanel.printAdminPage((Admin) user);
            } else {
                UserPanel.printUserPage(user);
            }
        } catch (WrongUsernameAndPasswordException e) {
            System.out.println(e.getMessage());
            UserAudit.addLog("Authorization", LocalDateTime.now(), "", UserAuditResult.FAIL);
            HomePanel.printStartPage();
        }
    }

    /**
     * Метод для получения пользователя по логину и паролю
     * @param username логин пользователя
     * @param password пароль пользователя
     * @throws WrongUsernameAndPasswordException в случае когда пользователя с таким логином и паролем не существует
     * @return {@link User}
     */
    private static User logIn(String username, String password) throws WrongUsernameAndPasswordException {
        return User.getUserFromUserSet(username, password);
    }
}
