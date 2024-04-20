package com.Y_LAB.homework.in.user_panel.auth_panel;

import com.Y_LAB.homework.dao.user.UserDAO;
import com.Y_LAB.homework.audit.UserAuditResult;
import com.Y_LAB.homework.in.user_panel.AdminPanel;
import com.Y_LAB.homework.in.user_panel.HomePanel;
import com.Y_LAB.homework.in.util.ConsoleReader;
import com.Y_LAB.homework.roles.Admin;
import com.Y_LAB.homework.entity.User;
import com.Y_LAB.homework.exception.auth.WrongUsernameAndPasswordException;
import com.Y_LAB.homework.in.user_panel.UserPanel;
import com.Y_LAB.homework.service.user.UserService;
import com.Y_LAB.homework.service.user.UserServiceImpl;


/**
 * Класс для вывода панели по авторизации пользователя и взаимодействия с ней
 * @author Денис Попов
 * @version 2.0
 */
public class AuthorizationPanel {

    /** Поле сервиса для взаимодействия с базой данных*/
    private static final UserService userService = UserServiceImpl.getService();

    private AuthorizationPanel() {}

    /**
     * Метод для вывода информации по авторизации пользователя,
     * считывает введённые данные пользователя с помощью метода <br>
     * {@link ConsoleReader#enterStringValue(int)},
     * в зависимости от типа полученного пользователя из метода <br> {@link UserDAO#getUser(String, String)}
     * вызывает соответствующий класс для пользователя - <br>
     * {@link AdminPanel#printAdminPage(Admin)}<br> или <br>{@link UserPanel#printUserPage(User)}<br>
     * для вывода информации по возможным действиям пользователя,
     * в случае когда пользователь по указанным логину и паролю не существует - вызывается домашняя страница
     * {@link HomePanel#printStartPage()}
     */
    public static void logOn() {
        System.out.print("Введите логин: ");
        String username = ConsoleReader.enterStringValue(0);
        System.out.print("Введите пароль: ");
        String password = ConsoleReader.enterStringValue(0);
        User user;
        try {
            user = userService.getUser(username, password);
            System.out.println("Вы успешно авторизовались как " + user.getUsername() + "\n");
            userService.saveUserAudit(user.getId(), "Авторизация", UserAuditResult.SUCCESS);
            if(user instanceof Admin){
                AdminPanel.printAdminPage((Admin) user);
            } else {
                UserPanel.printUserPage(user);
            }
        } catch (WrongUsernameAndPasswordException e) {
            System.out.println(e.getMessage());
            userService.saveUserAudit(null, "Авторизация", UserAuditResult.FAIL);
            HomePanel.printStartPage();
        }
    }
}
