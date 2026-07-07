package br.edu.ifba.inf008.dao;

import br.edu.ifba.inf008.model.Discount;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DiscountDAO {
    public void save(Session session, Discount discount) {
        Transaction tx = session.beginTransaction();
        session.persist(discount);
        tx.commit();
    }

    public void update(Session session, Discount discount) {
        Transaction tx = session.beginTransaction();
        session.merge(discount);
        tx.commit();
    }

    public void delete(Session session, Discount discount) {
        Transaction tx = session.beginTransaction();
        session.remove(discount);
        tx.commit();
    }

    public Discount findById(Session session, Long id) {
        return session.get(Discount.class, id);
    }

    public List<Discount> findAll(Session session) {
        return session.createQuery("FROM Discount", Discount.class).list();
    }
}