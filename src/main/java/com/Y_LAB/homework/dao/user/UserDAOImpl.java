package com.Y_LAB.homework.dao.user;

import com.Y_LAB.homework.entity.User;
import com.Y_LAB.homework.exception.auth.WrongUsernameAndPasswordException;
import com.Y_LAB.homework.roles.Admin;
import com.Y_LAB.homework.util.db.ConnectionToDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс реализовывает методы интерфейса {@link UserDAO}, который описывает DAO пользователей
 * @author Денис Попов
 * @version 1.0
 */
public class UserDAOImpl implements UserDAO {

    /** Поле для подключения к базе данных*/
    private final Connection connection;

    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public UserDAOImpl() {
        this.connection = ConnectionToDatabase.getConnection();
    }

    /**
     * Метод для получения всех пользователей из базы данных
     * @return Коллекция всех пользователей
     */
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet userResultSet = statement.executeQuery("SELECT * FROM training_diary.users");
            while (userResultSet.next()) {
                long id = userResultSet.getLong(1);
                String username = userResultSet.getString(2);
                String password = userResultSet.getString(3);
                if(isUserHasRoot(id))
                    users.add(new Admin(id, username, password));
                else
                    users.add(new User(id, username, password));
            }
        } catch (SQLException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
        return users;
    }

    /**
     * Метод для получения пользователя из базы данных
     * @param username логин пользователя
     * @param password пароль пользователя
     * @return пользователь
     * @throws WrongUsernameAndPasswordException когда пользователя с таким логином и паролем не существует
     */
    @Override
    public User getUser(String username, String password) throws WrongUsernameAndPasswordException {
        User user = null;
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT id FROM training_diary.users WHERE name = ? AND password = ?");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.execute();
            ResultSet userResultSet = statement.getResultSet();
            if(userResultSet.next()) {
                long id = userResultSet.getLong(1);
                if(isUserHasRoot(id))
                    user = new Admin(id, username, password);
                else
                    user = new User(id, username, password);
            }
            else {
                throw new WrongUsernameAndPasswordException("Неверный логин или пароль, повторите попытку");
            }
        } catch (SQLException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
        return user;
    }

    /**
     * Метод для проверки существования пользователя по логину в базе данных
     * @param username логин пользователя
     * @return True - пользователь с таким логином существует. False - пользователя с таким логином не существует
     */
    @Override
    public boolean isUserExist(String username) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM training_diary.users WHERE name = ?");
            statement.setString(1, username);
            statement.execute();
            ResultSet userResultSet = statement.getResultSet();
            if(userResultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
        return false;
    }

    /**
     * Метод для сохранения пользователя в базу данных
     * @param username логин пользователя
     * @param password пароль пользователя
     */
    @Override
    public void saveUser(String username, String password) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO training_diary.users (name, password) VALUES (?, ?)");

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
    }

    /**
     * Метод для обновления пользователя в базе данных
     * @param user пользователь
     */
    @Override
    public void updateUser(User user) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "UPDATE training_diary.users SET name = ?, password = ? WHERE id = ?");

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setLong(3, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
    }

    /**
     * Метод для удаления пользователя из базы данных
     * @param id идентификационный номер пользователя
     */
    @Override
    public void deleteUser(long id) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "DELETE FROM training_diary.users WHERE id = ?");
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
    }

    /**
     * Метод выявляет, является ли данный аккаунт администратором
     * @param id идентификационный номер аккаунта
     */
    private boolean isUserHasRoot(long id) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM training_diary.admins WHERE user_id = ?");
            statement.setLong(1, id);
            statement.execute();
            return statement.getResultSet().next();
        } catch (SQLException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
        return false;
    }
}
