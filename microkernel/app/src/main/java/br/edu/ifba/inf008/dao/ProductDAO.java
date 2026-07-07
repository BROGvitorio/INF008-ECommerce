package br.edu.ifba.inf008.dao;

import br.edu.ifba.inf008.model.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProductDAO {
    public void save(Session session, Product product) {
        Transaction tx = session.beginTransaction();
        session.persist(product);
        tx.commit();
    }

    public void update(Session session, Product product) {
        Transaction tx = session.beginTransaction();
        session.merge(product);
        tx.commit();
    }

    public void delete(Session session, Product product) {
        Transaction tx = session.beginTransaction();
        session.remove(product);
        tx.commit();
    }

    public Product findById(Session session, Long id) {
        return session.get(Product.class, id);
    }

    public List<Product> findAll(Session session) {
        return session.createQuery("FROM Product", Product.class).list();
    }
}