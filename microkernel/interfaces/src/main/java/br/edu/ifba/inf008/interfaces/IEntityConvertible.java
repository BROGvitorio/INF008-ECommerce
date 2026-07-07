package br.edu.ifba.inf008.interfaces;

public interface IEntityConvertible {
    Object toHibernateEntity();
    String getTableName();
}
