package br.edu.ifba.inf008.dao;

import br.edu.ifba.inf008.model.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OrderDAO {
    public void save(Session session, Order order) {
        Transaction tx = session.beginTransaction();
        session.persist(order);
        tx.commit();
    }

    public void update(Session session, Order order) {
        Transaction tx = session.beginTransaction();
        session.merge(order);
        tx.commit();
    }

    public void delete(Session session, Order order) {
        Transaction tx = session.beginTransaction();
        session.remove(order);
        tx.commit();
    }

    public Order findById(Session session, Long id) {
        return session.get(Order.class, id);
    }

    public List<Order> findAll(Session session) {
        return session.createQuery("FROM Order", Order.class).list();
    }
}