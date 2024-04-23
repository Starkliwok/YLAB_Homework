package com.Y_LAB.homework.in.user_panel;

import com.Y_LAB.homework.util.in.ConsoleReader;
import com.Y_LAB.homework.roles.Admin;
import com.Y_LAB.homework.entity.User;
import com.Y_LAB.homework.service.user.UserService;
import com.Y_LAB.homework.service.user.UserServiceImpl;

/**
 * Класс для вывода панели администратора и взаимодействия с ней
 * @author Денис Попов
 * @version 2.0
 */
public class AdminPanel {

    /** Поле администратора для взаимодействия с методами класса администратора.*/
    private static Admin admin;

    /** Поле сервиса для взаимодействия с базой данных*/
    private static final UserService userService = new UserServiceImpl();

    private AdminPanel() {}

    /**
     * Метод для вывода информации возможных действий администратора,
     * вызывает метод {@link AdminPanel#adminPageChooseAction()}
     * @param admin используется для присваивания объекта статическому полю {@link AdminPanel#admin}.
     */
    public static void printAdminPage(Admin admin) {
        AdminPanel.admin = admin;
        System.out.println("""
                 //Панель администратора//\s
                 Выберите действие:\s
                 1 - Посмотреть список пользователей\s
                 2 - Получить возможности пользователя\s
                 3 - Получить лог действий пользователей\s
                 0 - Выход из Аккаунта""");
        adminPageChooseAction();
    }

    /**
     * Метод для выбора действия администратора <br>
     * 1 - {@link AdminPanel#showAllUsers()} Посмотреть список пользователей<br>
     * 2 - {@link UserPanel#printUserPage(User)} Получить возможности пользователя<br>
     * 3 - {@link UserService#getAllUserAudits()} Получить лог действий пользователей<br>
     * 0 - {@link HomePanel#printStartPage()} Выход из Аккаунта<br>
     * любое другое значение выводит сообщение об ошибке и рекурсивно вызывает метод
     */
    private static void adminPageChooseAction() {
        switch (ConsoleReader.PageChoose()) {
            case 1 ->
                showAllUsers();
            case 2 ->
                UserPanel.printUserPage(admin);
            case 3 -> {
                userService.getAllUserAudits().forEach(System.out::println);
                printAdminPage(admin);
            }
            case 0 ->
                HomePanel.printStartPage();
            default -> {
                System.out.println("Некорректный ввод данных, повторите попытку");
                adminPageChooseAction();
            }
        }
    }

    /**
     * Метод выводит информацию о пользователях, а так же считывает выбор действия администратора с пользователями<br>
     * 1 - {@link AdminPanel#chooseUserToInteract()} Перейти к выбору пользователя<br>
     * 0 - {@link UserPanel#printUserPage(User)} Назад <br>
     * любое другое значение выводит сообщение об ошибке и вызывает метод <br> {@link AdminPanel#printAdminPage(Admin)}
     */
    private static void showAllUsers() {
        userService.getAllUsers().forEach(System.out::println);

        System.out.println("""
                 \nВыберите действие:\s
                 1 - Выбрать пользователя для взаимодействия с его аккаунтом\s
                 0 - Назад""");
        switch (ConsoleReader.PageChoose()) {
            case 1 ->
                chooseUserToInteract();
            case 0 ->
                UserPanel.printUserPage(admin);
            default -> {
                System.out.println("Некорректный ввод данных, повторите попытку");
                printAdminPage(admin);
            }
        }
    }

    /**
     * Метод предназначен для выбора пользователя и взаимодействии с ним.
     * Считывает введенный id пользователя администратором, получает объект пользователя основываясь на id,
     * в случае если пользователя с таким id не существует, то вызывается метод {@link AdminPanel#showAllUsers()}. <br>
     * Если же пользователь существует, то он проверяется на принадлежность к администратору, поскольку администратор
     * может взаимодействовать с пользователями, но администратор не может взаимодействовать с другим администратором,
     * в случае несоответствия вызывается метод {@link AdminPanel#showAllUsers()} <br>
     * исключение: если администратор выбирает самого себя для взаимодействия. <br>
     * вызывается метод {@link AdminPanel#chooseActionWithUser(User)}
     */
    private static void chooseUserToInteract() {
        System.out.println("Введите id пользователя для взаимодействия или любое другое число для выхода");
        int id = ConsoleReader.enterIntValue();
        User user = userService.getAllUsers().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        if(user == null) {
            showAllUsers();
        } else if(user instanceof Admin && id != admin.getId()) {
            System.out.println("Администратор не имеет доступа к изменению данных аккаунта другого администратора");
            showAllUsers();
        }
        chooseActionWithUser(user);
    }

    /**
     * Метод для выбора действия администратора с переданным пользователем <br>
     * 1 - используется для изменения логина пользователя, считывается новый логин, проверяется на наличие такого логина
     * у другого пользователя, в случае если логин не зарезервирован, то вызывается метод <br>
     * {@link UserService#updateUser(User)} для изменения логина, после изменения метод рекурсивно вызывается
     * для дальнейший действий с пользователем со стороны администратора <br>
     * 2 - используется для изменения пароля пользователя, считывается новый пароль вызывается метод <br>
     * {@link UserService#updateUser(User)} для изменения пароля, после изменения метод работает аналогично
     * как и метод по изменению логина <br>
     * 3 - вызывается метод {@link UserService#deleteUser(long)} в случае если указанный пользователь является
     * администратором, то удаляется его собственный аккаунт, почему так происходит описано в <br>
     * {@link AdminPanel#chooseUserToInteract()} в случае если пользователь не является администратором,
     * он так же удаляется <br>
     * 4 - {@link UserPanel#printUserPage(User)} используется для взаимодействием с дневником тренировок
     * выбранного пользователя<br>
     * 0 - {@link AdminPanel#showAllUsers()} возвращает на домашнюю страницу<br>
     * любое другое значение выводит сообщение об ошибке и рекурсивно вызывает метод
     * @param user пользователь, с которым происходит взаимодействие
     */
    private static void chooseActionWithUser(User user) {
        System.out.println("""
                Выберите действие:\s
                1 - Изменить логин пользователя\s
                2 - Изменить пароль пользователя\s
                3 - Удалить пользователя\s
                4 - Действия с дневником тренировок пользователя\s
                0 - Назад""");
        switch (ConsoleReader.PageChoose()) {
            case 1 -> {
                System.out.println("Введите новый логин");
                String newName = ConsoleReader.enterStringValue(3);
                if (!userService.getAllUsers().stream().filter
                        (user1 -> user1.getUsername().equals(newName)).toList().isEmpty()) {
                    System.out.println("Такой логин уже зарезервирован");
                } else {
                    user.setUsername(newName);
                    userService.updateUser(user);
                    System.out.println("Вы успешно изменили логин пользователя");
                }
                chooseActionWithUser(user);
            }
            case 2 -> {
                System.out.println("Введите новый пароль");
                String newPassword = ConsoleReader.enterStringValue(3);
                user.setPassword(newPassword);
                userService.updateUser(user);
                System.out.println("Вы успешно изменили пароль пользователя");
                chooseActionWithUser(user);
            }
            case 3 -> {
                userService.deleteUser(user.getId());
                if (user instanceof Admin) {
                    System.out.println("Вы удалили свой аккаунт");
                    HomePanel.printStartPage();
                }
                System.out.println("Вы успешно удалили пользователя");
                chooseUserToInteract();
            }
            case 4 ->
                UserPanel.printUserPage(user);
            case 0 ->
                showAllUsers();
            default -> {
                System.out.println("Некорректный ввод данных, повторите попытку");
                chooseActionWithUser(user);
            }
        }
    }
}