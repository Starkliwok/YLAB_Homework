package com.Y_LAB.homework.dao.training;


import com.Y_LAB.homework.entity.trainings.Training;
import com.Y_LAB.homework.util.db.ConnectionToDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс реализовывает методы интерфейса {@link AdditionalDataDAO}, который описывает DAO дополнительной информации
 * @author Денис Попов
 * @version 1.0
 */
public class TrainingDAOImpl implements TrainingDAO {

    /** Поле для подключения к базе данных*/
    private static Connection connection;

    /** Поле для получения объекта класса*/
    private static TrainingDAO trainingDAO;

    private TrainingDAOImpl() {}

    /** Метод для получения объекта класса в случае если объекта не существует, то создается новый объект
     * @return объекта класса
     * */
    public static TrainingDAO getInstance() {
        if(trainingDAO == null) {
            connection = ConnectionToDatabase.getConnection();
            trainingDAO = new TrainingDAOImpl();
        }
        return trainingDAO;
    }

    /**
     * Метод для получения всех тренировок пользователя из базы данных
     * @param userId идентификационный номер пользователя
     * @return Коллекция всех тренировок, которые принадлежат одному пользователю
     */
    @Override
    public List<Training> getAllTrainings(long userId) {
        List<Training> trainings = new ArrayList<>();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM training_diary.trainings WHERE user_id = ?");
            preparedStatement.setLong(1, userId);
            preparedStatement.execute();
            ResultSet trainingResultSet = preparedStatement.getResultSet();
                while (trainingResultSet.next()) {
                    Map<String, String> additionalDataList = new HashMap<>();
                    Training training = new Training(
                            trainingResultSet.getLong(1)
                            , trainingResultSet.getString(2)
                            , trainingResultSet.getString(3)
                            , trainingResultSet.getDate(4)
                            , trainingResultSet.getInt(5)
                            , trainingResultSet.getDouble(6)
                            , trainingResultSet.getLong(7));

                    setAdditionalData(training, additionalDataList);
                    trainings.add(training);
                }
        } catch (SQLException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
        return trainings;
    }

    /**
     * Метод для получения тренировки из базы данных
     * @param id идентификационный номер тренировки
     * @return Тренировка
     */
    @Override
    public Training getTraining(long id) {
        Training training = null;
        Map<String, String> additionalDataList = new HashMap<>();
        try {
            connection = ConnectionToDatabase.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM training_diary.trainings WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            ResultSet trainingResultSet = preparedStatement.getResultSet();

            if(trainingResultSet.next()) {
                training = new Training(
                        trainingResultSet.getLong(1)
                        , trainingResultSet.getString(2)
                        , trainingResultSet.getString(3)
                        , trainingResultSet.getDate(4)
                        , trainingResultSet.getInt(5)
                        , trainingResultSet.getDouble(6)
                        , trainingResultSet.getLong(7));

                setAdditionalData(training, additionalDataList);
            }
        } catch (SQLException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
        return training;
    }

    /**
     * Метод для получения из базы данных и присваивания дополнительной информации тренировке
     * @param training тренировка
     * @param additionalDataList коллекция дополнительной информации
     */
    private void setAdditionalData(Training training, Map<String, String> additionalDataList)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM training_diary.additional_data WHERE training_id = ?");
        preparedStatement.setLong(1, training.getId());
        preparedStatement.execute();
        ResultSet additionalDataResultSet = preparedStatement.getResultSet();

        if(additionalDataResultSet != null) {
            while (additionalDataResultSet.next()) {
                additionalDataList.put(
                        additionalDataResultSet.getString(2)
                        , additionalDataResultSet.getString(3));
            }
        }
        training.setAdditionalDataMap(additionalDataList);
    }

    /**
     * Метод для сохранения тренировки в базу данных
     * @param training тренировка
     */
    @Override
    public void saveTraining(Training training) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO training_diary.trainings " +
                                    "VALUES (NEXTVAL('training_diary.training_id_seq'), ?, ?, ?, ?, ?, ?)");

            insertTrainingDataToPreparedStatement(training, preparedStatement);
            preparedStatement.setLong(6, training.getUserId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
    }

    /**
     * Метод для обновления тренировки в базе данных
     * @param training тренировка
     */
    @Override
    public void updateTraining(Training training) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "UPDATE training_diary.trainings SET name = ?, type = ?, date = ?, calories_spent = ?" +
                                    ", duration_in_minutes = ? WHERE id = ?");

            insertTrainingDataToPreparedStatement(training, preparedStatement);
            preparedStatement.setLong(6, training.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
    }
    /**
     * Метод для сокращения кода в некоторых методах данного класса
     * @param training тренировка
     * @param preparedStatement объект для взаимодействия с базой данных
     * @throws SQLException в случае если preparedStatement закрыт
     */
    private void insertTrainingDataToPreparedStatement
            (Training training, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, training.getName());
        preparedStatement.setString(2, training.getType());
        preparedStatement.setDate(3, new Date(training.getDate().getTime()));
        preparedStatement.setInt(4, training.getCaloriesSpent());
        preparedStatement.setDouble(5, training.getDurationInMinutes());
    }

    /**
     * Метод для удаления тренировки из базы данных
     * @param id идентификационный номер тренировки
     */
    @Override
    public void deleteTraining(long id) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "DELETE FROM training_diary.trainings WHERE id = ?");
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
    }
}
