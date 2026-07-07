package br.edu.ifba.inf008.dao;

import br.edu.ifba.inf008.model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CustomerDAO {
    public void save(Session session, Customer customer) {
        Transaction tx = session.beginTransaction();
        session.persist(customer);
        tx.commit();
    }

    public void update(Session session, Customer customer) {
        Transaction tx = session.beginTransaction();
        session.merge(customer);
        tx.commit();
    }

    public void delete(Session session, Customer customer) {
        Transaction tx = session.beginTransaction();
        session.remove(customer);
        tx.commit();
    }

    public Customer findById(Session session, Long id) {
        return session.get(Customer.class, id);
    }

    public List<Customer> findAll(Session session) {
        return session.createQuery("FROM Customer", Customer.class).list();
    }
}