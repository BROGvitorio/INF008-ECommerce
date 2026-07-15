package br.edu.ifba.inf008.plugins;

import java.util.Map;
import java.math.BigDecimal;
import java.util.HashMap;

import br.edu.ifba.inf008.domain.Cart;
import br.edu.ifba.inf008.domain.CartItem;
import br.edu.ifba.inf008.domain.Customer;
import br.edu.ifba.inf008.domain.Product;
import br.edu.ifba.inf008.domain.StockMovement;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IPersistenceController;
import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.plugins.Exceptions.InsufficientStockException;
import br.edu.ifba.inf008.plugins.Exceptions.NotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CartPlugin implements IPlugin {
    private IPersistenceController persistenceController;

    private Cart cart = null;
    private Map<CartItem, StockMovement> cartStockMovements = new HashMap<CartItem, StockMovement>();

    private Customer testCustomer;

    public boolean init() {
        persistenceController = ICore.getInstance().getPersistenceController();

        testCustomer = persistenceController.findById(Customer.class, Long.valueOf(4));
        if (testCustomer == null) {
            testCustomer = new Customer("Cart Test Customer", "cart.test.customer@email.com", "STUDENT");
            persistenceController.save(testCustomer);
        }

        CartView.createMenuItem(
            "Cart", 
            "Create",
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    createCart();
                }
            }
        );

        CartView.createMenuItem(
            "Cart", 
            "AddItem1",
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    addCartItem((long) 1);
                }
            }
        );

        CartView.createMenuItem(
            "Cart", 
            "AddItem2",
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    addCartItem((long) 2);
                }
            }
        );
 
        return true ;
    } 
 
    public void createCart () {
        try {
           if (cart != null)
                throw new IllegalStateException("You already have a shipping cart.");
                
            cart = new Cart (
                testCustomer,
                "OPEN"
            ); 

            persistenceController.save(cart);
            CartView.createCartTab(
                cart,
                () -> cancelCart(),
                () -> checkout(),
                this::updateCartItemQuantity
            );
            updateView();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void cancelCart () {
        CartView.cancelCartTab();
        persistenceController.delete(cart.getClass(), cart.getId());
        cart = null;
    }

    private void checkStock (long productId, int change) {
        int stock = 0;
        Product p = persistenceController.findById(Product.class, Long.valueOf(productId));

        if (p == null) {
            throw new NotFoundException("No product could be found with that ID.");
        }

        for (StockMovement sm : persistenceController.findAll(StockMovement.class)) {
            if (sm.getProduct().getId().equals(p.getId()))
                stock += "INBOUND".equals(sm.getMovementType()) ? sm.getQuantity() : sm.getQuantity() * -1;
        }

        if (stock < change)
            throw new InsufficientStockException(stock);
    }

    private void updateView () {
        CartView.updateTable(cart);
        CartView.updateSummary(() -> getItemsCount(), () -> getSubtotal());
    }

    public void addCartItem (long productId) {
        try {
            if (cart == null)
                throw new NotFoundException("You haven't a shipping cart already.");

            Product p = persistenceController.findById(Product.class, Long.valueOf(productId));

            checkStock(p.getId(), 1);

            CartItem ci = new CartItem (cart, p, Integer.valueOf(1), p.getUnitPrice());
            StockMovement sm = new StockMovement(p, "RESERVED", Integer.valueOf(1), "Cart reservation");

            persistenceController.save(sm);
            persistenceController.save(ci);

            cartStockMovements.put(ci, sm);
            cart.addItem(ci);

            CartView.showErrorMessage("");
            updateView();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateCartItemQuantity (CartItem item, int change) {
        try {
            if (item.getQuantity() == null)
                throw new IllegalStateException("The item has an invalid quantity.");

            if (item.getQuantity() + change <= 0) {
                persistenceController.delete(StockMovement.class, cartStockMovements.get(item).getId());
                persistenceController.delete(CartItem.class, item.getId());

                cart.removeItem(item);
                cartStockMovements.remove(item);

                updateView();
                return;
            }
            
            if (change > 0)
                checkStock (
                    item.getProduct().getId(), 
                    change
                );
            
            StockMovement sm = cartStockMovements.get(item);

            item.setQuantity(item.getQuantity() + change);
            sm.setQuantity(item.getQuantity());
            
            persistenceController.update(sm);
            persistenceController.update(item);
            
            CartView.showErrorMessage("");
            updateView();
        } catch (InsufficientStockException e) {
            CartView.showErrorMessage(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public BigDecimal getSubtotal () {
        BigDecimal subtotal = new BigDecimal(0);

        for (CartItem ci : cart.getItems()) {
            subtotal = subtotal.add(
                ci
                .getUnitPrice()
                .multiply(BigDecimal.valueOf(ci.getQuantity()))
            );
        }

        return subtotal;
    }

    public int getItemsCount () {
        int sum = 0;

        for (CartItem ci : cart.getItems()) {
            sum += ci.getQuantity();
        }
        
        return sum;
    }

    public void checkout () {

    }
}