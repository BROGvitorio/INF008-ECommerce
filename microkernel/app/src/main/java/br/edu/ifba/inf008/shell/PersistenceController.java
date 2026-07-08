package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.database.HibernateUtil;
import br.edu.ifba.inf008.interfaces.IPersistenceController;

import org.hibernate.Session;

import java.util.List;
import java.util.NoSuchElementException;

public class PersistenceController implements IPersistenceController {

    private void checkEntity (Object record) {
        if (!record.getClass()
            .isAnnotationPresent(jakarta.persistence.Entity.class))
            throw new IllegalArgumentException("This object isn't a Entity.");
    }

    public void save(Object entity) {
        checkEntity(entity);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) 
        {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
        } catch (Exception e) 
        {
            throw new RuntimeException("Erro ao salvar entidade: " + e.getMessage(), e);
        }
    }

    public void update(Object entity) {
        checkEntity(entity);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) 
        {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
        } catch (Exception e) 
        {
            throw new RuntimeException("Erro ao atualizar entidade: " + e.getMessage(), e);
        }
    }

    public void delete(Class<?> clazz, Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) 
        {
            session.beginTransaction();
            
            Object entity = session.find(clazz, id);
            if (entity == null) {
                throw new NoSuchElementException(clazz.getSimpleName() + " com id " + id + " não encontrado.");
            }

            session.remove(entity);
            session.getTransaction().commit();
        } catch (Exception e) 
        {
            throw new RuntimeException("Erro ao deletar entidade: " + e.getMessage(), e);
        }
    }

    public <T> T findById(Class<T> clazz, Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) 
        {
            return session.find(clazz, id);
        }
    }

    public <T> List<T> findAll(Class<T> clazz) {
        try (Session session = HibernateUtil.getSessionFactory().openSession())
        {
            String query = "FROM " + clazz.getSimpleName().toLowerCase() + "s";
            return session.createQuery(query, clazz).list();
        }
    }

}
