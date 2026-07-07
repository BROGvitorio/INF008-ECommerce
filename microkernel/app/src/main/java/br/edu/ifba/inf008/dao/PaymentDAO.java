package br.edu.ifba.inf008.dao;

import br.edu.ifba.inf008.model.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PaymentDAO {
    public void save(Session session, Payment entity) {
        Transaction tx = session.beginTransaction();
        session.persist(entity);
        tx.commit();
    }

    public void update(Session session, Payment entity) {
        Transaction tx = session.beginTransaction();
        session.merge(entity);
        tx.commit();
    }

    public void delete(Session session, Payment entity) {
        Transaction tx = session.beginTransaction();
        session.remove(entity);
        tx.commit();
    }

    public Payment findById(Session session, Long id) {
        return session.get(Payment.class, id);
    }

    public List<Payment> findAll(Session session) {
        return session.createQuery("FROM Payment", Payment.class).list();
    }
}