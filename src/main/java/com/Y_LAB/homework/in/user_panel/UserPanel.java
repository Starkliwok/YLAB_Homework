package com.Y_LAB.homework.in.user_panel;

import com.Y_LAB.homework.audit.UserAudit;
import com.Y_LAB.homework.audit.UserAuditResult;
import com.Y_LAB.homework.in.util.ConsoleReader;
import com.Y_LAB.homework.roles.Admin;
import com.Y_LAB.homework.roles.User;
import com.Y_LAB.homework.trainings.Training;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Класс для вывода панели пользователя
 * @author Денис Попов
 * @version 1.0
 */
public class UserPanel {

    /** Поле пользователя для взаимодействия с методами класса пользователя.*/
    private static User user;

    /** Поле даты для взаимодействия с тренировками пользователя.*/
    private static Date date;

    private UserPanel() {}

    /**
     * Метод выводит информацию о возможных действиях пользователя, а так же дополнительную информацию для 
     * администратора, который использует панель как обычный пользователь
     */
    public static void printUserPage(User user) {
        UserPanel.user = user;
        System.out.print("""
                 Выберите действие:\s
                 1 - Посмотреть список тренировок\s
                 2 - Посмотреть статистику потраченных калорий на тренировках\s
                 3 - Добавить тренировку\s
                 4 - Удалить тренировку\s
                 5 - Редактировать тренировку\s
                 0 - Выход из аккаунта""");
        if(user instanceof Admin) {
            System.out.println(" пользователя и переход в панель администратора");
        }
        System.out.println();
        UserPanel.userPageChooseAction();
    }

    /**
     * Метод считывает ввод пользователя и на его основе вызывает методы <br>
     * 1 - {@link UserPanel#printTrainingList()} Посмотреть список тренировок<br>
     * 2 - {@link UserPanel#printTrainingCaloriesBurnedStatistics()} Посмотреть статистику потраченных калорий на тренировках<br>
     * 3 - {@link UserPanel#printAddTrainingPage()} Добавить тренировку<br>
     * 4 - {@link UserPanel#deleteTraining()} Удалить тренировку<br>
     * 5 - {@link UserPanel#updateTraining()} Редактировать тренировку<br>
     * 0 - Выход из аккаунта / В случае, если это администратор, то переводит его в панель администратора<br>
     * В других случаях выводит информацию о некорректном вводе данных и рекурсивно вызывает метод
     */
    private static void userPageChooseAction() {
        switch (ConsoleReader.PageChoose()) {
            case 1 ->
                printTrainingList();
            case 2 ->
                printTrainingCaloriesBurnedStatistics();
            case 3 ->
                printAddTrainingPage();
            case 4 ->
                deleteTraining();
            case 5 ->
                updateTraining();
            case 0 -> {
                if (user instanceof Admin)
                    AdminPanel.printAdminPage((Admin) user);
                UserAudit.addLog("Exit from the app", LocalDateTime.now(), user.getUsername(), UserAuditResult.SUCCESS);
                HomePanel.printStartPage();
            }
            default -> {
                System.out.println("Некорректный ввод данных, повторите попытку");
                userPageChooseAction();
            }
        }
    }

    /**
     * Метод выводит все тренировки отсортированные по дате, в случае если тренировок нет, выводит сообщение и
     * отправляет на домашнюю панель пользователя
     */
    private static void printTrainingList() {
        if(user.getTrainingHistory().isEmpty()) {
            System.out.println("Тренировок пока что нету, добавьте их\n1");
            UserAudit.addLog("print trainings (training list is empty)", LocalDateTime.now()
                    , user.getUsername(), UserAuditResult.FAIL);
            printUserPage(user);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Лист тренировок отсортированных убыванию даты: \n");

        Comparator<Date> descByDate = (o1, o2) -> o1.after(o2) ? -1 : o1.equals(o2) ? 0 : 1;

        List<Date> allSortedDatesFromTrainingHistory = new ArrayList<>(user.getTrainingHistory().keySet().stream().toList())
                .stream().sorted(descByDate).toList();

        for(Date date : allSortedDatesFromTrainingHistory) {
            System.out.println("\n" + formatter.format(date));
            user.getTrainingHistory().get(date).forEach(System.out::println);
            System.out.println("---------------------------------------------------");
        }
        UserAudit.addLog("print trainings", LocalDateTime.now()
                , user.getUsername(), UserAuditResult.SUCCESS);

        printUserPage(user);
    }

    /**
     * Метод считывает ввод пользователя - начальную дату и конечную дату, после чего выводит информацию о том, 
     * сколько пользователь сжег калорий за этот промежуток времени, после вывода информации переводит пользователя 
     * на домашнюю панель пользователя
     */
    private static void printTrainingCaloriesBurnedStatistics() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Введите начальную дату");
        Date start = ConsoleReader.enterDate();
        Date iterableDate = new Date(start.getTime());

        System.out.println("Введите конечную дату");
        Date end = ConsoleReader.enterDate();

        Map<Date, List<Training>> trainingHistory = user.getTrainingHistory();
        int result = 0;
        while (!iterableDate.after(end)) {
            if (trainingHistory.containsKey(iterableDate)) {
                List<Training> trainingList = trainingHistory.get(iterableDate);
                for(Training training : trainingList) {
                    result += training.getCaloriesSpent();
                }
            }
            iterableDate = new Date(iterableDate.getTime() + 24 * 60 * 60 * 1000);
        }
        System.out.println("С " + formatter.format(start) + " по " + formatter.format(end) + " вы сожгли - "
                + result + " калорий");
        UserAudit.addLog("print burned calories stats", LocalDateTime.now()
                , user.getUsername(), UserAuditResult.SUCCESS);
        printUserPage(user);
    }

    /**
     * Метод считывает ввод пользователя - дату тренировки, вызывает <br>{@link TrainingPanel#enterTraining(User, Date)}
     * для создания объекта тренировки и его получение, после чего добавляет тренировку в дневник<br>
     * {@link User#addTrainingToHistory(Date, Training)}, в случае успешного добавления выводит информацию об этом,
     * после чего переводит пользователя на домашнюю панель пользователя
     */
    private static void printAddTrainingPage() {
        date = ConsoleReader.enterDate();
        Training training = TrainingPanel.enterTraining(user, date);

        user.addTrainingToHistory(date, training);
        System.out.println("Тренировка успешно добавлена\n");

        UserAudit.addLog("add training to history", LocalDateTime.now()
                , user.getUsername(), UserAuditResult.SUCCESS);

        printUserPage(user);
    }

    /**
     * Метод проверяет по ранее указанной дате, есть ли в дневнике за указанную дату тренировки и выводит все
     * тренировки, а так же выводит к каждой тренировке уникальный номер, в случае если тренировок за эту дату нет, то
     * выведется информация об ошибке и пользователя перенаправят на домашнюю панель пользователя
     * @param trainingList тренировки, которые нужно вывести пользователю
     */
    private static void printTrainingPage(List<Training> trainingList) {
        if(user.getTrainingHistory().containsKey(date)) {
            for(int i = 0; i < trainingList.size(); i++) {
                System.out.println(trainingList.get(i) + "\n" + "Номер тренировки - " + (i + 1));
            }
        } else {
            System.out.println("В вашем дневнике нету такой даты с тренировками, попробуйте ещё раз");
            printUserPage(user);
        }
    }

    /**
     * Метод считывает ввод пользователя в виде даты и получает за эту дату все тренировки, после чего вызывает метод
     * {@link UserPanel#printTrainingPage(List)} и вызывает метод для выбора номера тренировки и получает необходимую
     * тренировку по её номеру, в случае если такой тренировки нет -> пользователя перенаправляет на панель пользователя
     * @param outputAction тренировки, которые нужно вывести пользователю
     * @return тренировка, которую выбрал пользователь
     */
    private static Training chooseTraining(String outputAction) {
        List<Training> trainingList = findTrainingsByDate();

        printTrainingPage(trainingList);
        System.out.println("\nВыберите номер тренировки или любое другое число для отмены");
        int trainingIndex = ConsoleReader.PageChoose();
        Training training = getTraining(trainingList, trainingIndex);

        if (training != null) {
            System.out.println(training + "\n\nДля " + outputAction + " этой тренировки введите любое число\nНазад - 0");
            int choose = ConsoleReader.PageChoose();
            if (choose == 0)
                return chooseTraining(outputAction);
            training = trainingList.get(trainingIndex - 1);
        } else {
            System.out.println("Вы отменили действие " + outputAction);
            printUserPage(user);
        }
        return training;
    }

    /**
     * Метод вызывает {@link UserPanel#chooseTraining(String)} для того, что бы пользователь выбрал тренировку,
     * которую он хочет удалить, после чего тренировка удаляется из дневника пользователя и пользователя перенаправляют
     * на домашнюю панель пользователя
     */
    private static void deleteTraining() {
        Training training = chooseTraining("удаления");
        user.deleteTraining(date, training);
        System.out.println("\nВы успешно удалили тренировку");
        UserAudit.addLog("delete training", LocalDateTime.now()
                , user.getUsername(), UserAuditResult.SUCCESS);
        printUserPage(user);
    }

    /**
     * Метод вызывает {@link UserPanel#chooseTraining(String)} для того, что бы пользователь выбрал тренировку,
     * которую он хочет изменить, после чего пользователю необходимо выбрать номер какого поля он хочет изменить,
     * после чего вызывается метод по изменению полей тренировки {@link UserPanel#updateField(Training)}
     */
    private static void updateTraining() {
        Training training = chooseTraining("изменения");
        System.out.println(training + "5 - Изменить тип тренировки\n0 - Выход");
        updateField(training);
        System.out.println("\nВы успешно изменили тренировку");
        UserAudit.addLog("update training", LocalDateTime.now()
                , user.getUsername(), UserAuditResult.SUCCESS);
        printUserPage(user);
    }

    /**
     * Метод считывает ввод пользователя - какое именно поле он хочет изменить <br>
     * 1 - Название тренировки <br>
     * 2 - Количество потраченных калорий <br>
     * 3 - Длительность в минутах <br>
     * 4 - Изменить дополнительные поля <br>
     * 5 - Изменить тип тренировки <br>
     * 0 - Назад <br>
     * любое другое число рекурсивно вызывает метод.
     * После чего вызывает соответствующие методы для считывания ввода нового значения и его присваивания выбранному
     * полю тренировки, после успешного обновления информации предлагает пользователю изменить ещё значения
     * где - <br>
     * 1 - Изменить ещё<br>
     * любое другое значение - перенаправляет пользователя на панель пользователя
     * @param training тренировка, которую изменяет пользователь
     */
    private static void updateField(Training training) {
        System.out.println("\nВведите номер поля для изменения:");
        switch (ConsoleReader.enterIntValue()){
            case 1 -> {
                System.out.print("Введите новое значение для поля: ");
                training.setName(ConsoleReader.enterStringValue(3));
                UserAudit.addLog("update training name", LocalDateTime.now()
                        , user.getUsername(), UserAuditResult.SUCCESS);
            }
            case 2 -> {
                System.out.print("Введите новое значение для поля: ");
                training.setCaloriesSpent(ConsoleReader.enterIntValue());
                UserAudit.addLog("update training calories spent", LocalDateTime.now()
                        , user.getUsername(), UserAuditResult.SUCCESS);
            }
            case 3 -> {
                System.out.print("Введите новое значение для поля: ");
                training.setDurationInMinutes(ConsoleReader.enterDoubleValue());
                UserAudit.addLog("update training duration in minutes", LocalDateTime.now()
                        , user.getUsername(), UserAuditResult.SUCCESS);
            }
            case 4 ->
                    updateAdditionalFields(training);
            case 5 -> {
                System.out.println("Введите новое значение для поля: ");
                training.setType(ConsoleReader.enterStringValue(3));
            }
            case 0 ->
                    chooseTraining("изменения");
            default -> {
                System.out.println("Число не соответствует полю, попробуйте ещё раз");

                UserAudit.addLog("update training", LocalDateTime.now()
                        , user.getUsername(), UserAuditResult.FAIL);
                updateField(training);
            }
        }
        System.out.println("""
                 Вы успешно изменили поле:\s
                 1 - Изменить ещё\s
                 Для завершения изменения полей введите любой символ""");
        if(ConsoleReader.PageChoose() == 1){
            System.out.println(training + "\n\n 0 - Выход");
            updateField(training);
        }
        printUserPage(user);
    }

    /**
     * Метод получает и выводит названия всех дополнительных информаций о тренировках, после чего считывает ввод
     * пользователя, где ввод пользователя равен названию дополнительной информации. <br>
     * В случае, если пользователь вводит достоверное название поля, то считывается ввод пользователя, где ввод
     * пользователя равен новому значению для этой дополнительной информации, после чего информация в тренировке
     * обновляется. <br>
     * В случае, если пользователь вводит недостоверное название поля, то выводится информация об ошибке и метод
     * вызывается рекурсивно
     * @param training тренировка, которую изменяет пользователь
     */
    private static void updateAdditionalFields(Training training) {
        Map<String, String> dataMap = training.getAdditionalDataMap();
        for(String key : dataMap.keySet()) {
            System.out.println(key);
        }
        System.out.println("\nВведите имя поля которое хотите изменить: ");
        String name = ConsoleReader.enterStringValue(2);
        if (dataMap.containsKey(name)) {
            System.out.println("Введите новое значение для этого поля: ");
            String value = ConsoleReader.enterStringValue(2);
            dataMap.put(name, value);
        } else {
            System.out.println("Такого поля не существует, попробуйте ещё раз");
            updateAdditionalFields(training);
        }

    }

    /**
     * Метод проверяет параметр номера тренировки на соответствие, после чего получает тренировку из списка тренировок,
     * если же ввод не соответствует реальному номеру тренировки, то возвращается null
     * @param trainingList тренировки
     * @param trainingIndex номер тренировки
     * @return Возвращает тренировку
     */
    private static Training getTraining(List<Training> trainingList, int trainingIndex) {
        return trainingList.size() > trainingIndex - 1
                        && trainingIndex > 0
                        ? trainingList.get(trainingIndex - 1) : null;
    }

    /**
     * Метод считывает ввод пользователя, где ввод это дата, после чего получает за указанную дату список тренировок
     * @return Возвращает тренировки за указанную дату
     */
    private static List<Training> findTrainingsByDate() {
        return user.getTrainingHistory().get(ConsoleReader.enterDate());
    }
}
