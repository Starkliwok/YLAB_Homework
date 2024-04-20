package com.Y_LAB.homework.in.user_panel;

import com.Y_LAB.homework.audit.UserAuditResult;
import com.Y_LAB.homework.entity.trainings.AdditionalData;
import com.Y_LAB.homework.in.util.ConsoleReader;
import com.Y_LAB.homework.roles.Admin;
import com.Y_LAB.homework.entity.User;
import com.Y_LAB.homework.entity.trainings.Training;
import com.Y_LAB.homework.service.training.TrainingService;
import com.Y_LAB.homework.service.training.TrainingServiceImpl;
import com.Y_LAB.homework.service.user.UserService;
import com.Y_LAB.homework.service.user.UserServiceImpl;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Класс для вывода панели пользователя
 * @author Денис Попов
 * @version 2.0
 */
public class UserPanel {

    /** Поле пользователя для взаимодействия с методами класса пользователя.*/
    private static User user;

    /** Поле даты для взаимодействия с тренировками пользователя.*/
    private static Date date;

    /** Поле сервиса для взаимодействия с базой данных*/
    private static final TrainingService trainingService = TrainingServiceImpl.getService();

    /** Поле сервиса для взаимодействия с базой данных*/
    private static final UserService userService = UserServiceImpl.getService();

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
     * 3 - {@link UserPanel#AddTraining()} Добавить тренировку<br>
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
                AddTraining();
            case 4 ->
                deleteTraining();
            case 5 ->
                updateTraining();
            case 0 -> {
                if (user instanceof Admin)
                    AdminPanel.printAdminPage((Admin) user);
                userService.saveUserAudit(user.getId(), "Выход из аккаунта", UserAuditResult.SUCCESS);
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
        if(trainingService.getAllTrainings(user.getId()).isEmpty()) {
            System.out.println("Тренировок пока что нету, добавьте их\n1");
            userService.saveUserAudit(user.getId(), "Вывод тренировок (тренировок нету)", UserAuditResult.FAIL);
            printUserPage(user);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Лист тренировок отсортированных убыванию даты: \n");

        Comparator<Date> descByDate = (o1, o2) -> o1.after(o2) ? -1 : o1.equals(o2) ? 0 : 1;

        Set<Date> allSortedDatesFromTrainingHistory = new HashSet<>(trainingService.getAllTrainings(user.getId())
                .stream()
                .map(Training::getDate)
                .sorted(descByDate)
                .toList());

        for(Date date : allSortedDatesFromTrainingHistory) {
            System.out.println("\n" + formatter.format(date));
            trainingService.getAllTrainings(user.getId()).stream().filter(training -> training.getDate().equals(date))
                    .forEach(System.out::println);
            System.out.println("---------------------------------------------------");
        }
        userService.saveUserAudit(user.getId(), "Вывод тренировок", UserAuditResult.SUCCESS);

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
        Date startDate = ConsoleReader.enterDate();
        Date iterableDate = new Date(startDate.getTime());

        System.out.println("Введите конечную дату");
        Date endDate = ConsoleReader.enterDate();

        List<Training> trainingHistory = trainingService.getAllTrainings(user.getId());
        int result = 0;
        while (!iterableDate.after(endDate)) {
            Date finalIterableDate = iterableDate;
            if (!trainingHistory.stream().filter(tr -> tr.getDate().equals(finalIterableDate)).toList().isEmpty()) {
                List<Training> trainingList =
                        trainingHistory.stream().filter(tr -> tr.getDate().equals(finalIterableDate)).toList();
                for(Training training : trainingList) {
                    result += training.getCaloriesSpent();
                }
            }
            iterableDate = new Date(iterableDate.getTime() + 24 * 60 * 60 * 1000);
        }
        System.out.println("С " + formatter.format(startDate) + " по " + formatter.format(endDate) + " вы сожгли - "
                + result + " калорий");
        userService.saveUserAudit(user.getId(), "Вывод количества сожженных калорий", UserAuditResult.SUCCESS);

        printUserPage(user);
    }

    /**
     * Метод считывает ввод пользователя - дату тренировки, вызывает <br>{@link TrainingPanel#enterTraining(User, Date)}
     * для создания объекта тренировки и его получение, после чего добавляет тренировку в дневник<br>
     * {@link TrainingService#saveTraining(Training)}, в случае успешного добавления выводит информацию об этом,
     * после чего переводит пользователя на домашнюю панель пользователя
     */
    private static void AddTraining() {
        date = ConsoleReader.enterDate();
        Training training = TrainingPanel.enterTraining(user, date);

        trainingService.saveTraining(training);

        System.out.println("Тренировка успешно добавлена\n");
        userService.saveUserAudit(user.getId(), "Добавление тренировки", UserAuditResult.SUCCESS);
        printUserPage(user);
    }

    /**
     * Метод проверяет по ранее указанной дате, есть ли в дневнике за указанную дату тренировки и выводит все
     * тренировки, а так же выводит к каждой тренировке уникальный номер, в случае если тренировок за эту дату нет, то
     * выведется информация об ошибке и пользователя перенаправят на домашнюю панель пользователя
     * @param trainingList тренировки, которые нужно вывести пользователю
     */
    private static void printTrainingsByDate(List<Training> trainingList) {
        if(!trainingService.getAllTrainings(user.getId())
                .stream()
                .filter(training -> training.getDate().equals(date))
                .toList().isEmpty()) {
            for(int i = 0; i < trainingList.size(); i++) {
                System.out.print("\nНомер тренировки - " + (i + 1) + trainingList.get(i));
            }
        } else {
            System.out.println("В вашем дневнике нету такой даты с тренировками, попробуйте ещё раз");
            printUserPage(user);
        }
    }

    /**
     * Метод считывает ввод пользователя в виде даты и получает за эту дату все тренировки, после чего вызывает метод
     * {@link UserPanel#printTrainingsByDate(List)} и вызывает метод для выбора номера тренировки и получает необходимую
     * тренировку по её номеру, в случае если такой тренировки нет -> пользователя перенаправляет на панель пользователя
     * @param outputAction тренировки, которые нужно вывести пользователю
     * @return тренировка, которую выбрал пользователь
     */
    private static Training chooseTraining(String outputAction) {

        date = ConsoleReader.enterDate();

        List<Training> trainingList = trainingService.getAllTrainings(user.getId())
                .stream()
                .filter(training -> training.getDate().equals(date))
                .toList();

        printTrainingsByDate(trainingList);
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

        trainingService.deleteTraining(training.getId());

        System.out.println("\nВы успешно удалили тренировку");
        userService.saveUserAudit(user.getId(), "Удаление тренировки", UserAuditResult.SUCCESS);
        printUserPage(user);
    }

    /**
     * Метод вызывает {@link UserPanel#chooseTraining(String)} для того, что бы пользователь выбрал тренировку,
     * которую он хочет изменить, после чего пользователю необходимо выбрать номер какого поля он хочет изменить,
     * после чего вызывается метод по изменению полей тренировки {@link UserPanel#updateField(Training)}
     */
    private static void updateTraining() {
        Training training = chooseTraining("изменения");
        System.out.println(training + "0 - Выход");
        updateField(training);
        System.out.println("\nВы успешно изменили тренировку");
        userService.saveUserAudit(user.getId(), "Обновление тренировки id = " + training.getId()
                , UserAuditResult.SUCCESS);
        printUserPage(user);
    }

    /**
     * Метод считывает ввод пользователя - какое именно поле он хочет изменить <br>
     * 1 - Изменить тип тренировки<br>
     * 2 - Название тренировки <br>
     * 3 - Количество потраченных калорий <br>
     * 4 - Длительность в минутах <br>
     * 5 - Изменить дополнительные поля <br>
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
                training.setType(ConsoleReader.enterStringValue(3));
                userService.saveUserAudit(user.getId(), "Обновление типа тренировки id = " + training.getId()
                        , UserAuditResult.SUCCESS);
            }
            case 2 -> {
                System.out.print("Введите новое значение для поля: ");
                training.setName(ConsoleReader.enterStringValue(3));
                userService.saveUserAudit(user.getId(), "Обновление имени тренировки id = " + training.getId()
                        , UserAuditResult.SUCCESS);
            }
            case 3 -> {
                System.out.print("Введите новое значение для поля: ");
                training.setCaloriesSpent(ConsoleReader.enterIntValue());
                userService.saveUserAudit(user.getId(), "Обновление количества сожженных калорий тренировки id = "
                                + training.getId(), UserAuditResult.SUCCESS);
            }
            case 4 -> {
                System.out.print("Введите новое значение для поля: ");
                training.setDurationInMinutes(ConsoleReader.enterDoubleValue());
                userService.saveUserAudit(user.getId(), "Обновление длительности в минутах тренировки id = "
                        + training.getId(), UserAuditResult.SUCCESS);
            }
            case 5 ->
                changeAdditionalFields(training);
            case 0 ->
                    chooseTraining("изменения");
            default -> {
                System.out.println("Число не соответствует полю, попробуйте ещё раз");

                userService.saveUserAudit(user.getId(), "Обновление полей тренировки id = "
                        + training.getId(), UserAuditResult.FAIL);
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
        trainingService.updateTraining(training);
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
    private static void changeAdditionalFields(Training training) {
        Map<String, String> additionalDataMap = training.getAdditionalDataMap();
        if(additionalDataMap.isEmpty()) {
            TrainingPanel.chooseToAddAdditionalDataToTraining(user, training);
            printUserPage(user);
        }
        for(String key : additionalDataMap.keySet()) {
            System.out.println(key);
        }
        System.out.println("\nВыберите действие " +
                "\n1 - Изменить дополнительную информацию " +
                "\n2 - Удалить дополнительную информацию" +
                "\n3 - Добавить новую дополнительную информацию" +
                "\nЛюбое другое число - Назад");
        switch(ConsoleReader.enterIntValue()) {
            case 1 ->
                updateAdditionalField(additionalDataMap, training);
            case 2 ->
                deleteAdditionalField(additionalDataMap, training);
            case 3 ->
                    TrainingPanel.chooseToAddAdditionalDataToTraining(user, training);
            default ->
                    updateField(training);
        }
    }

    /**
     * Метод для обновления дополнительной информации, в случае если введённое название существует, то
     * обновляется значение этой дополнительной информации и вызывается метод
     * {@link TrainingService#updateAdditionalData(AdditionalData)}, в случае если такого поля не существует, то
     * вызывается метод {@link UserPanel#changeAdditionalFields(Training)}
     * @param additionalDataMap дополнительная информация
     * @param training тренировка, которую изменяет пользователь
     */
    private static void updateAdditionalField(Map<String, String> additionalDataMap, Training training) {
        System.out.println("\nВведите имя поля которое хотите изменить: ");
        String name = ConsoleReader.enterStringValue(2);
        if (additionalDataMap.containsKey(name)) {
            System.out.println("Введите новое значение для этого поля: ");
            String value = ConsoleReader.enterStringValue(2);
            AdditionalData additionalData = trainingService.getAdditionalData(name, additionalDataMap.get(name),
                    training.getId());
            additionalDataMap.put(name, value);
            additionalData.setValue(value);
            trainingService.updateAdditionalData(additionalData);
        } else {
            System.out.println("Такого поля не существует, попробуйте ещё раз");
            changeAdditionalFields(training);
        }
    }

    /**
     * Метод для удаления дополнительной информации, в случае если введённое название существует, то
     * дополнительная информация удаляется вызовом метода, в случае если такого поля не существует, то
     * вызывается метод {@link UserPanel#changeAdditionalFields(Training)}
     * {@link TrainingService#deleteAdditionalData(long)}
     * @param additionalDataMap дополнительная информация
     * @param training тренировка, которую изменяет пользователь
     */
    private static void deleteAdditionalField(Map<String, String> additionalDataMap, Training training) {
        System.out.println("\nВведите имя поля которое хотите удалить: ");
        String name = ConsoleReader.enterStringValue(2);
        if (additionalDataMap.containsKey(name)) {
            AdditionalData additionalData = trainingService.getAdditionalData(name, additionalDataMap.get(name),
                    training.getId());
            additionalDataMap.remove(name);
            trainingService.deleteAdditionalData(additionalData.getId());
        } else {
            System.out.println("Такого поля не существует, попробуйте ещё раз");
            changeAdditionalFields(training);
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
}
