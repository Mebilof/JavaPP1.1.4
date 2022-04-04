package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static Session session;
    private Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        session = Util.configureSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users(id SERIAL PRIMARY KEY, name VARCHAR(255), lastName VARCHAR (255), age INT)").executeUpdate();
        transaction.commit();
    }

    @Override
    public void dropUsersTable() {
        session = Util.configureSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF  EXISTS  Users;").executeUpdate();
        transaction.commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        session = Util.configureSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        transaction.commit();
    }

    @Override
    public void removeUserById(long id) {
        session = Util.configureSessionFactory().openSession();
        session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        transaction.commit();
    }

    @Override
    public List<User> getAllUsers() {
        session = Util.configureSessionFactory().openSession();
        List<User> list = session.createQuery("from User").getResultList();
        return list;
    }

    @Override
    public void cleanUsersTable() {
        session = Util.configureSessionFactory().openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM User").executeUpdate();
        transaction.commit();
    }
}
