package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.domain.Cart;
import br.edu.ifba.inf008.domain.CartItem;
import br.edu.ifba.inf008.domain.Customer;
import br.edu.ifba.inf008.domain.Product;
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

        CartView.createMenuItem(
            "Cart", 
            "AddItem1",
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    addCartItem((long) 1, 1);
                }
            }
        );

        CartView.createMenuItem(
            "Cart", 
            "AddItem2",
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    addCartItem((long) 2, 2);
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

    public void addCartItem (long productId, int quantity) {
        try {
            if (currentCart == null)
                throw new IllegalStateException("You haven't a shipping cart already.");

            Product p = persistenceController.findById(Product.class, new Long((productId)));
            CartItem ci = new CartItem (currentCart, p, new Integer(quantity), p.getUnitPrice());
    
            currentCart.addItem(ci);
            persistenceController.save(ci);
            CartView.updateTable(currentCart);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void addCartItem (long productId) {
        addCartItem(productId, 1);
    }
}