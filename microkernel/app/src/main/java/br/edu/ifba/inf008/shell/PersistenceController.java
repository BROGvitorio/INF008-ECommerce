package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.database.HibernateUtil;
import br.edu.ifba.inf008.interfaces.IEntityConvertible;
import br.edu.ifba.inf008.interfaces.IPersistenceController;

import org.hibernate.Session;

import java.util.List;

public class PersistenceController implements IPersistenceController {

    public void save(IEntityConvertible record) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Object entity = record.toHibernateEntity();
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
            session.close();

        }
    }

    public void update(IEntityConvertible record) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Object entity = record.toHibernateEntity();

            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
        }
    }

    public void delete(IEntityConvertible record) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Object entity = record.toHibernateEntity();

            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
        }
    }

    public <T> T findById(Class<T> clazz, Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.find(clazz, id);
    }

    public <T> List<T> findAll(String tableName, Class<T> clazz) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String query = "FROM " + tableName;
        return session.createQuery(query, clazz).list();
    }

}
