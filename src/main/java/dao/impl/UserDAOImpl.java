package dao.impl;

import dao.UserDAO;
import model.User;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateUtil;

public class UserDAOImpl implements UserDAO {

    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class.getName());

    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    private static final String QUERY_SELECT_FROM_USER = "from User";

    @Override
    public void save(User user) {
        Transaction transaction = null;

        try (Session session = getSession()) {
            transaction = session.beginTransaction();

            session.save(user);

            transaction.commit();
        } catch (HibernateException e) {
            LOGGER.error("Request to save new user has failed. Error message: {}", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public User getByLogin(String login) {
        return getAll().stream()
                .filter(user -> login.equals(user.getLogin()))
                .findAny()
                .orElse(new User("Unknown"));
    }

    @Override
    public User getByLoginAndPassword(String login, String password) {
        return getAll().stream()
                .filter(user -> login.equals(user.getLogin())
                        && password.equals(user.getPassword()))
                .findAny()
                .orElse(new User("Unknown"));
    }

    private List<User> getAll() {
        List<User> users = new ArrayList<>();

        try (Session session = getSession()) {
            users = session.createQuery(QUERY_SELECT_FROM_USER).list();
        } catch (HibernateException e) {
            LOGGER.error("Request to get all users has failed. Error message: {}", e.getMessage());
        }

        return users;
    }

    private Session getSession() {
        return sessionFactory.openSession();
    }
}
