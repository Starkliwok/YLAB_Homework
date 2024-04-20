package com.Y_LAB.homework.dao.training;

import com.Y_LAB.homework.entity.trainings.AdditionalData;
import com.Y_LAB.homework.util.db.ConnectionToDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс реализовывает методы интерфейса {@link AdditionalDataDAO}, который описывает DAO дополнительной информации
 * @author Денис Попов
 * @version 1.0
 */
public class AdditionalDataDAOImpl implements AdditionalDataDAO {

    /** Поле для подключения к базе данных*/
    private final Connection connection = ConnectionToDatabase.getConnection();

    /** Поле для получения объекта класса*/
    private static AdditionalDataDAO additionalDataDAO;

    private AdditionalDataDAOImpl() {}

    /** Метод для получения объекта класса в случае если объекта не существует, то создается новый объект
     * @return объекта класса
     * */
    public static AdditionalDataDAO getInstance() {
        if(additionalDataDAO == null) {
            additionalDataDAO = new AdditionalDataDAOImpl();
        }
        return additionalDataDAO;
    }

    /**
     * Метод для получения всей дополнительной информации тренировки из базы данных
     * @param trainingId идентификационный номер тренировки
     * @return Коллекция всей дополнительной информации, которая принадлежит одной тренировке
     */
    @Override
    public List<AdditionalData> getAllAdditionalData(long trainingId) {
        List<AdditionalData> additionalDataList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM training_diary.additional_data WHERE training_id = ?");
            preparedStatement.setLong(1, trainingId);
            preparedStatement.execute();
            ResultSet additionalDataResultSet = preparedStatement.getResultSet();
            while (additionalDataResultSet.next()) {
                AdditionalData additionalData = new AdditionalData(
                        additionalDataResultSet.getLong(1)
                        , additionalDataResultSet.getString(2)
                        , additionalDataResultSet.getString(3)
                        , additionalDataResultSet.getLong(4));

                additionalDataList.add(additionalData);
            }
        } catch (SQLException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
        return additionalDataList;
    }

    /**
     * Метод для получения дополнительной информации тренировки из базы данных
     * @param name название дополнительной информации
     * @param value значение дополнительной информации
     * @param trainingId идентификационный номер тренировки
     * @return дополнительная информация, которая принадлежит тренировке
     */
    @Override
    public AdditionalData getAdditionalData(String name, String value, long trainingId) {
        AdditionalData additionalData = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM training_diary.additional_data " +
                            "WHERE name = ? AND value = ? AND training_id = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, value);
            preparedStatement.setLong(3, trainingId);

            preparedStatement.execute();
            ResultSet additionalDataResultSet = preparedStatement.getResultSet();
            if(additionalDataResultSet.next()) {
                additionalData = new AdditionalData(
                        additionalDataResultSet.getLong(1)
                        , additionalDataResultSet.getString(2)
                        , additionalDataResultSet.getString(3)
                        , additionalDataResultSet.getLong(4));
            }
        } catch (SQLException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
        return additionalData;
    }

    /**
     * Метод для сохранения дополнительной информации тренировки в базу данных
     * @param name название дополнительной информации
     * @param value значение дополнительной информации
     * @param trainingId идентификационный номер тренировки
     */
    @Override
    public void saveAdditionalData(String name, String value, long trainingId){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO training_diary.additional_data " +
                                    "VALUES (NEXTVAL('training_diary.additional_data_id_seq'), ?, ?, ?)");

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, value);
            preparedStatement.setLong(3, trainingId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
    }

    /**
     * Метод для обновления дополнительной информации тренировки в базе данных
     * @param additionalData дополнительная информация
     */
    @Override
    public void updateAdditionalData(AdditionalData additionalData){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "UPDATE training_diary.additional_data SET name = ?, value = ? WHERE id = ?");

            preparedStatement.setString(1, additionalData.getName());
            preparedStatement.setString(2, additionalData.getValue());
            preparedStatement.setLong(3, additionalData.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
    }

    /**
     * Метод для удаления дополнительной информации тренировки из базы данных
     * @param id номер дополнительной информации
     */
    @Override
    public void deleteAdditionalData(long id) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "DELETE FROM training_diary.additional_data WHERE id = ?");

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
    }
}
