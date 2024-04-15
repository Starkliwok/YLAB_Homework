package com.Y_LAB.homework.in.user_panel;

import com.Y_LAB.homework.audit.UserAudit;
import com.Y_LAB.homework.audit.UserAuditResult;
import com.Y_LAB.homework.in.util.ConsoleReader;
import com.Y_LAB.homework.roles.User;
import com.Y_LAB.homework.trainings.AdditionalData;
import com.Y_LAB.homework.trainings.Training;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Класс для вывода панели дневника тренировок
 * @author Денис Попов
 * @version 1.0
 */
public class TrainingPanel {

    private TrainingPanel() {}

    /**
     * Метод для создания тренировки в дневнике пользователя
     * параметр дат тренировки выявляет - тренировка в прошедшем или будущем времени, в случае если она в будущем
     * времени, выводится информация о то что тренировка будет запланированной, а так же не запрашивается ввод
     * данных тренировки (количество потраченных калорий, длительность тренировки), если же тренировка в прошедшем
     * времени, тогда на данные поля запрашивается ввод и тренировка принимает эти значения.
     * Также вызываются методы для присваивания типа тренировки, а так же дополнительной информации, которую
     * пользователь хотел бы указать.
     * @param user владелец дневника тренировок
     * @param date дата тренировки
     * @return возвращает тренировку с заполненными полями, если она в прошедшем времени, если в будущем, то поля
     * заполняются частично
     */
    public static Training enterTraining(User user, Date date) {
        boolean isPlanned = date.after(new Date(System.currentTimeMillis()));
        if(isPlanned) {
            System.out.println("Тренировка будет добавлена в качестве запланированной" +
                ", вы сможете добавлять дополнительные параметры к данной тренировке" +
                ", а так же по прошествию тренировки вносить результаты");
        }
        Training training = chooseTypeOfTraining(user, date);

        System.out.print("Введите название тренировки: ");
        training.setName(ConsoleReader.enterStringValue(3));
        if(!isPlanned) {
            System.out.print("Введите количество потраченных калорий: ");
            training.setCaloriesSpent(ConsoleReader.enterIntValue());
            System.out.print("Введите длительность тренировки в минутах: ");
            training.setDurationInMinutes(ConsoleReader.enterDoubleValue());
        }
        chooseToAddAdditionalDataToTraining(user, training);

        return training;
    }

    /**
     * Метод для создания добавления типа тренировки в тренировку, при этом за одну дату не могут быть
     * одинаковых тренировок, в случае если пользователь пытается указать тип тренировки
     * который уже существует в данной дате, тогда выводится сообщение об ошибке, а так же вызывается метод для
     * вывода панели пользователя - {@link UserPanel#printUserPage(User)}
     * @param user владелец дневника тренировок
     * @param date дата тренировки
     * @return возвращает тренировку с заполненным типом тренировки
     */
    public static Training chooseTypeOfTraining(User user, Date date) {
        Training training = null;
        System.out.print("Введите тип тренировки: ");
        String type = ConsoleReader.enterStringValue(3);

        if(containsTypeOfTrainingInUserHistory(user, date, type)) {
            System.out.println("Нельзя создавать тренировки одинаковых типов в один день");
            UserAudit.addLog("add type data to training", LocalDateTime.now()
                    , user.getUsername(), UserAuditResult.FAIL);
            UserPanel.printUserPage(user);
        } else {
            training = new Training();
            training.setType(type);
        }
        return training;
    }

    /**
     * Метод для обнаружения типа тренировки в указанной дате
     * @param user владелец дневника тренировок
     * @param date дата тренировки
     * @param type тип тренировки
     * @return true - если метод обнаружил тип тренировки, false - если метод не обнаружил тип тренировки
     */
    private static boolean containsTypeOfTrainingInUserHistory(User user, Date date, String type) {
        if(user.getTrainingHistory().containsKey(date)) {
            List<Training> trainingList = user.getTrainingHistory().get(date);
            for (Training createdTraining : trainingList) {
                if (createdTraining.getType().equals(type)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Метод считывает данные для уточнения информации, хочет ли пользователь добавить дополнительную информацию
     * в тренировку,<br>
     * 1 - {@link TrainingPanel#addAdditionalDataToTraining(User, Training)} добавить информацию<br>
     * 0 - не добавлять информацию (выход из метода)
     * остальной ввод - вывод информацию о некорректном вводе данных и рекурсивно вызывает метод
     * @param user владелец дневника тренировок
     * @param training тренировка
     */
    public static void chooseToAddAdditionalDataToTraining(User user, Training training) {
        System.out.println("""
                Хотите добавить дополнительную информацию о тренировке?\s
                1 - Да\s
                0 - Нет""");
        switch (ConsoleReader.PageChoose()) {
            case 1 ->
                addAdditionalDataToTraining(user, training);
            case 0 -> {}
            default -> {
                System.out.println("Некорректный ввод данных, повторите попытку");
                UserAudit.addLog("add additional data to training", LocalDateTime.now()
                        , user.getUsername(), UserAuditResult.FAIL);
                chooseToAddAdditionalDataToTraining(user, training);
            }
        }
    }

    /**
     * Метод для добавления дополнительной информации в тренировку, считывает введённый данные пользователя
     * вызывает метод {@link TrainingPanel#chooseToAddAdditionalDataToTraining(User, Training)} для возможности
     * повторного добавления информации
     * @param user владелец дневника тренировок
     * @param training тренировка
     */
    public static void addAdditionalDataToTraining(User user, Training training) {
        System.out.print("\nВведите название дополнительной информации: ");
        String name = ConsoleReader.enterStringValue(3);

        System.out.print("Введите значение дополнительной информации: ");
        String value = ConsoleReader.enterStringValue(0);

        training.addAdditionalData(new AdditionalData(name, value));
        System.out.println("Дополнительная информация успешно добавлена, вы можете добавить ещё");

        UserAudit.addLog("add additional data to training", LocalDateTime.now()
                , user.getUsername(), UserAuditResult.SUCCESS);
        chooseToAddAdditionalDataToTraining(user, training);
    }
}
