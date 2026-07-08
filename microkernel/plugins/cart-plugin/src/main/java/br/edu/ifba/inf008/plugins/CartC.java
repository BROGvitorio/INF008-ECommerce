package br.edu.ifba.inf008.plugins;

import java.lang.Long;

import br.edu.ifba.inf008.domain.Cart;
import br.edu.ifba.inf008.domain.Customer;
import br.edu.ifba.inf008.interfaces.IEntityConvertible;
import java.time.LocalDateTime;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IPersistenceController;

class CartC implements IEntityConvertible {
    private long id;
    private long customer;
    private String status;
    private LocalDateTime createdAt;

    public CartC (String status) {
        this.status = status;
    }

    @Override
    public Object toHibernateEntity() {
        IPersistenceController persistence = ICore.getInstance().getPersistenceController();
        Cart newCart = new Cart(
            persistence.findById(Customer.class, new Long((long) 1)),
            status);
            
            return newCart;
    }
        
    @Override
    public String getTableName() { return "cars"; }
}