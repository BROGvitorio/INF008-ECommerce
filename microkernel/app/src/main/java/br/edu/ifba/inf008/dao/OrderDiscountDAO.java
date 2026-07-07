package br.edu.ifba.inf008.dao;

import br.edu.ifba.inf008.model.OrderDiscount;
import br.edu.ifba.inf008.model.OrderDiscountId;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OrderDiscountDAO {
    public void save(Session session, OrderDiscount entity) {
        Transaction tx = session.beginTransaction();
        session.persist(entity);
        tx.commit();
    }

    public void update(Session session, OrderDiscount entity) {
        Transaction tx = session.beginTransaction();
        session.merge(entity);
        tx.commit();
    }

    public void delete(Session session, OrderDiscount entity) {
        Transaction tx = session.beginTransaction();
        session.remove(entity);
        tx.commit();
    }

    public OrderDiscount findById(Session session, OrderDiscountId id) {
        return session.get(OrderDiscount.class, id);
    }

    public List<OrderDiscount> findAll(Session session) {
        return session.createQuery("FROM OrderDiscount", OrderDiscount.class).list();
    }
}