package br.edu.ifba.inf008.dao;

import br.edu.ifba.inf008.model.OrderItem;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OrderItemDAO {
    public void save(Session session, OrderItem entity) {
        Transaction tx = session.beginTransaction();
        session.persist(entity);
        tx.commit();
    }

    public void update(Session session, OrderItem entity) {
        Transaction tx = session.beginTransaction();
        session.merge(entity);
        tx.commit();
    }

    public void delete(Session session, OrderItem entity) {
        Transaction tx = session.beginTransaction();
        session.remove(entity);
        tx.commit();
    }

    public OrderItem findById(Session session, Long id) {
        return session.get(OrderItem.class, id);
    }

    public List<OrderItem> findAll(Session session) {
        return session.createQuery("FROM OrderItem", OrderItem.class).list();
    }
}