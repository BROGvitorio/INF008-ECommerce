package br.edu.ifba.inf008.dao;

import br.edu.ifba.inf008.model.StockMovement;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StockMovementDAO {
    public void save(Session session, StockMovement stockMovement) {
        Transaction tx = session.beginTransaction();
        session.persist(stockMovement);
        tx.commit();
    }

    public void update(Session session, StockMovement stockMovement) {
        Transaction tx = session.beginTransaction();
        session.merge(stockMovement);
        tx.commit();
    }

    public void delete(Session session, StockMovement stockMovement) {
        Transaction tx = session.beginTransaction();
        session.remove(stockMovement);
        tx.commit();
    }

    public StockMovement findById(Session session, Long id) {
        return session.get(StockMovement.class, id);
    }

    public List<StockMovement> findAll(Session session) {
        return session.createQuery("FROM StockMovement", StockMovement.class).list();
    }
}