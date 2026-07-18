package br.edu.ifba.inf008.interfaces;
import java.util.List;

public interface IPersistenceController {
    void save(Object record);
    void update(Object record);
    void delete(Class<?> clazz, Long id);
    <T> T findById(Class<T> clazz, Long id);
    <T> List<T> findAll(Class<T> clazz);
    <T> List<T> findByName(Class<T> clazz, String name);
    <T> List<T> findBy(Class<T> clazz, String attribute, Object object);
}