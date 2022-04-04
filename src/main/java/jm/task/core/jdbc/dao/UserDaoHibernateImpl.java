package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static Session session;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        session = Util.configureSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users(id SERIAL PRIMARY KEY, name VARCHAR(255), lastName VARCHAR (255), age INT)").executeUpdate();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        session = Util.configureSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF  EXISTS  Users;").executeUpdate();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        session = Util.configureSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        session = Util.configureSessionFactory().openSession();
        session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        session.beginTransaction();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        session = Util.configureSessionFactory().openSession();
        session.beginTransaction();
        List<User> list = session.createQuery("from User").getResultList();
        session.close();
        return list;
    }

    @Override
    public void cleanUsersTable() {
        session = Util.configureSessionFactory().openSession();
        session.beginTransaction();
        session.createQuery("delete User").executeUpdate();
        session.close();
    }
}
