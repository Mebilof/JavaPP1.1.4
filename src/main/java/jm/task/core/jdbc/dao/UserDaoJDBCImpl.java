package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {


    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()){
            statement.executeUpdate("CREATE TABLE Users(id SERIAL, name VARCHAR(25), lastName VARCHAR (30), age INT)");
            connection.commit();
        } catch (SQLException e) {
            try {
                getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()){
            statement.executeUpdate("DROP TABLE IF EXISTS Users");
            connection.commit();
        } catch (SQLException e) {
            try {
                getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Users(name, lastName, age) VALUES (?, ?, ?)")){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Users WHERE id = ?")){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection(); Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery("SELECT id, name, lastName, age FROM Users ")){
            while (result.next()) {
                User user = new User();
                user.setId(result.getLong("id"));
                user.setName(result.getString("name"));
                user.setLastName(result.getString("lastName"));
                user.setAge(result.getByte("age"));
                users.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()){
            statement.executeUpdate("TRUNCATE TABLE Users ");
            connection.commit();
        } catch (SQLException e) {
            try {
                getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
