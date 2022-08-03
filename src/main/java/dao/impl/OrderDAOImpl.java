package dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import dao.OrderDAO;
import model.Order;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateUtil;

public class OrderDAOImpl implements OrderDAO {

    private static final Logger LOGGER = LogManager.getLogger(OrderDAOImpl.class.getName());

    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(Order order) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.save(order);

            transaction.commit();
        } catch (HibernateException e) {
            LOGGER.error("Request to save new order has failed. Error message: {}", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
