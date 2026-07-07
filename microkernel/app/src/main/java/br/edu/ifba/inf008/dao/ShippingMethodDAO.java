package br.edu.ifba.inf008.dao;

import br.edu.ifba.inf008.model.ShippingMethod;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ShippingMethodDAO {
    public void save(Session session, ShippingMethod shippingMethod) {
        Transaction tx = session.beginTransaction();
        session.persist(shippingMethod);
        tx.commit();
    }

    public void update(Session session, ShippingMethod shippingMethod) {
        Transaction tx = session.beginTransaction();
        session.merge(shippingMethod);
        tx.commit();
    }

    public void delete(Session session, ShippingMethod shippingMethod) {
        Transaction tx = session.beginTransaction();
        session.remove(shippingMethod);
        tx.commit();
    }

    public ShippingMethod findById(Session session, Long id) {
        return session.get(ShippingMethod.class, id);
    }

    public List<ShippingMethod> findAll(Session session) {
        return session.createQuery("FROM ShippingMethod", ShippingMethod.class).list();
    }
}