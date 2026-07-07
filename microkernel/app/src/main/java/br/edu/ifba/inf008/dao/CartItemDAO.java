package br.edu.ifba.inf008.dao;

import br.edu.ifba.inf008.model.CartItem;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CartItemDAO {
    public void save(Session session, CartItem cartItem) {
        Transaction tx = session.beginTransaction();
        session.persist(cartItem);
        tx.commit();
    }

    public void update(Session session, CartItem cartItem) {
        Transaction tx = session.beginTransaction();
        session.merge(cartItem);
        tx.commit();
    }

    public void delete(Session session, CartItem cartItem) {
        Transaction tx = session.beginTransaction();
        session.remove(cartItem);
        tx.commit();
    }

    public CartItem findById(Session session, Long id) {
        return session.get(CartItem.class, id);
    }

    public List<CartItem> findAll(Session session) {
        return session.createQuery("FROM CartItem", CartItem.class).list();
    }
}