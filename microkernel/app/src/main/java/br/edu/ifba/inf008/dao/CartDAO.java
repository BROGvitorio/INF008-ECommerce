package br.edu.ifba.inf008.dao;

import br.edu.ifba.inf008.model.Cart;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CartDAO {
    public void save(Session session, Cart cart) {
        Transaction tx = session.beginTransaction();
        session.persist(cart);
        tx.commit();
    }

    public void update(Session session, Cart cart) {
        Transaction tx = session.beginTransaction();
        session.merge(cart);
        tx.commit();
    }

    public void delete(Session session, Cart cart) {
        Transaction tx = session.beginTransaction();
        session.remove(cart);
        tx.commit();
    }

    public Cart findById(Session session, Long id) {
        return session.get(Cart.class, id);
    }

    public List<Cart> findAll(Session session) {
        return session.createQuery("FROM Cart", Cart.class).list();
    }
}