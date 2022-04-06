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
        try {
            session = Util.configureSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users(id SERIAL PRIMARY KEY, name VARCHAR(255), lastName VARCHAR (255), age INT)").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
        session = Util.configureSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF  EXISTS  Users;").executeUpdate();
        transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try {
        session = Util.configureSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        try{
        session = Util.configureSessionFactory().openSession();
        transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        session = Util.configureSessionFactory().openSession();
        List<User> list = session.createQuery("from User").getResultList();
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try {
        session = Util.configureSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.createQuery("DELETE FROM User").executeUpdate();
        transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }
}
