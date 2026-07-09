package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.domain.Cart;
import br.edu.ifba.inf008.domain.Customer;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IPersistenceController;
import br.edu.ifba.inf008.interfaces.IPlugin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CartPlugin implements IPlugin {
    private IPersistenceController persistenceController;

    private Cart currentCart = null;

    public boolean init() {
        persistenceController = ICore.getInstance().getPersistenceController();

        CartView.createMenuItem(
            "Cart", 
            "Create",
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    createCart();
                    CartView.createCartTab(currentCart, () -> cancelCart());
                }
            }
        );
 
        return true ;
    } 
 
    public void createCart () {
        try {
           if (currentCart != null)
                throw new IllegalStateException("You already have a shipping cart.");
                
            currentCart = new Cart (
                persistenceController.findById(Customer.class, new Long((long) 1)),
                "OPEN"
            ); 

            persistenceController.save(currentCart);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void cancelCart () {
        CartView.cancelCartTab();
        persistenceController.delete(currentCart.getClass(), currentCart.getId());
        currentCart = null;
    }
}