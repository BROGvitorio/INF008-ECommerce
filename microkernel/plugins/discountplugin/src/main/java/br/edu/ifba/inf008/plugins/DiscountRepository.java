package br.edu.ifba.inf008.plugins;

import org.hibernate.Session;

public class DiscountRepository
{
    public Discount findActiveCoupon(String code) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Discount WHERE lower(code) = :code AND active = true";

            return session.createQuery(hql, Discount.class)
                .setParameter("code", code.toLowerCase())
                .uniqueResult();
        }
    }
}