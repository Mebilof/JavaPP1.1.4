package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static Statement statement;

    public UserDaoJDBCImpl() {

    }

    static {
        try {
            statement = Util.getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createUsersTable() {
        try {
            statement.executeUpdate("CREATE TABLE users(id BIGINT(7) NOT NULL PRIMARY KEY AUTO_INCREMENT,name VARCHAR(25),lastName VARCHAR (30),age TINYINT(3))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            statement.executeUpdate("DROP TABLE IF EXISTS Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = Util.getConnection().prepareStatement("INSERT INTO Users(name, lastName, age) VALUES (?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = Util.getConnection().prepareStatement("DELETE FROM Users WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        ResultSet result = null;
        try {
            result = statement.executeQuery("SELECT id, name, lastName, age FROM Users ");
            while (result.next()) {
                User user = new User();
                user.setId(result.getLong("id"));
                user.setAge(result.getByte("age"));
                user.setName(result.getString("name"));
                user.setLastName(result.getString("last_name"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            statement.executeUpdate("TRUNCATE TABLE Users ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
