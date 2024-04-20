package com.Y_LAB.homework.dao.audit;

import com.Y_LAB.homework.audit.UserAuditResult;
import com.Y_LAB.homework.dao.user.UserDAOImpl;
import com.Y_LAB.homework.entity.UserAudit;
import com.Y_LAB.homework.util.db.ConnectionToDatabase;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс реализовывает методы интерфейса {@link UserAuditDAO}, который описывает DAO аудитов пользователей
 * @author Денис Попов
 * @version 1.0
 */
public class UserAuditDAOImpl implements UserAuditDAO {

    /** Поле для подключения к базе данных*/
    private final Connection connection = ConnectionToDatabase.getConnection();

    /** Поле для получения объекта класса*/
    private static UserAuditDAO userAuditDAO;

    private UserAuditDAOImpl() {}

    /** Метод для получения объекта класса в случае если объекта не существует, то создается новый объект
     * @return объекта класса
     * */
    public static UserAuditDAO getInstance() {
        if(userAuditDAO == null) {
            userAuditDAO = new UserAuditDAOImpl();
        }
        return userAuditDAO;
    }

    /**
     * Метод для получения всех аудитов пользователей из базы данных
     * @return Коллекция всех аудитов пользователей
     */
    @Override
    public List<UserAudit> getAllUserAudits() {
        List<UserAudit> userAudits = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet userAuditResultSet = statement.executeQuery("SELECT * FROM training_diary.user_audit");
            while (userAuditResultSet.next()) {
                userAudits.add(new UserAudit(
                                        userAuditResultSet.getInt(1)
                                        , userAuditResultSet.getTimestamp(2).toLocalDateTime()
                                        , userAuditResultSet.getLong(3)
                                        , userAuditResultSet.getString(4)
                                        , userAuditResultSet.getString(5)));
            }
        } catch (SQLException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
        return userAudits;
    }

    /**
     * Метод для получения аудита пользователя из базы данных
     * @param id идентификационный номер аудита
     * @return аудитов пользователя
     */
    @Override
    public UserAudit getUserAudit(long id) {
        UserAudit userAudit = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM training_diary.user_audit WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

            ResultSet userAuditResultSet = preparedStatement.getResultSet();
            if(userAuditResultSet.next()) {
                userAudit = new UserAudit(
                        userAuditResultSet.getInt(1)
                        , userAuditResultSet.getTimestamp(2).toLocalDateTime()
                        , userAuditResultSet.getLong(3)
                        , userAuditResultSet.getString(4)
                        , userAuditResultSet.getString(5));
            }
        } catch (SQLException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
        return userAudit;
    }

    /**
     * Метод для получения аудита пользователя из базы данных
     * @param userId идентификационный номер пользователя
     * @param action действия пользователя
     * @param userAuditResult результат действий пользователя
     */
    @Override
    public void saveUserAudit(Long userId, String action, UserAuditResult userAuditResult) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO training_diary.user_audit " +
                            "VALUES (NEXTVAL('training_diary.user_audit_id_seq'), ?, ?, ?, ?)");

            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            if (userId == null) {
                preparedStatement.setNull(2, Types.BIGINT);
            } else {
                preparedStatement.setLong(2, userId);
            }
            preparedStatement.setString(3, action);
            preparedStatement.setString(4, userAuditResult.name());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
    }
}
