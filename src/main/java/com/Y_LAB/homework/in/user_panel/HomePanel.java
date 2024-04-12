package com.Y_LAB.homework.in.user_panel;

import com.Y_LAB.homework.audit.UserAudit;
import com.Y_LAB.homework.audit.UserAuditResult;
import com.Y_LAB.homework.in.auth_panel.AuthorizationPanel;
import com.Y_LAB.homework.in.auth_panel.RegistrationPanel;
import com.Y_LAB.homework.in.util.ConsoleReader;

import java.time.LocalDateTime;

/**
 * Класс для вывода панели с авторизацией и регистрацией
 * @author Денис Попов
 * @version 1.0
 */
public class HomePanel {

    private HomePanel() {}

    /**
     * Метод для вывода панели регистрации и авторизации,
     * вызывает метод {@link HomePanel#startPageChooseAction()}
     */
    public static void printStartPage() {
        System.out.println("""
                Здравствуйте, вас приветствует дневник тренировок\s
                 Выберите действие:\s
                 1 - Авторизация\s
                 2 - Регистрация\s
                 0 - Выход из приложения""");
        startPageChooseAction();
    }

    /**
     * Метод считывает ввод пользователя и на его основе вызывает методы <br>
     * 1 - {@link AuthorizationPanel#printLogInPage()} Авторизация<br>
     * 2 - {@link RegistrationPanel#printRegistrationPage()} Регистрация<br>
     * 0 - Завершение работы приложения<br>
     * В других случаях выводит информацию о некорректном вводе данных и рекурсивно вызывает метод
     */
    private static void startPageChooseAction() {
        switch (ConsoleReader.PageChoose()) {
            case 1:
                AuthorizationPanel.printLogInPage();
                break;
            case 2:
                RegistrationPanel.printRegistrationPage();
                break;
            case 0:
                UserAudit.addLog("exit", LocalDateTime.now(), "", UserAuditResult.SUCCESS);
                System.exit(0);
                break;
            default:
                System.out.println("Некорректный ввод данных, повторите попытку");
                startPageChooseAction();
        }
    }
}
