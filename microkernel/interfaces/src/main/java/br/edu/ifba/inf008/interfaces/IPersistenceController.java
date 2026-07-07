package br.edu.ifba.inf008.interfaces;
import java.util.List;

public interface IPersistenceController {
    void save(IEntityConvertible record);
    void update(IEntityConvertible record);
    void delete(IEntityConvertible record);
    <T> T findById(Class<T> clazz, Long id);
    <T> List<T> findAll(String tableName, Class<T> clazz);
}