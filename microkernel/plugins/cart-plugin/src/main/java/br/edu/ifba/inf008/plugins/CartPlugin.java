package br.edu.ifba.inf008.plugins;

import java.util.Map;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import br.edu.ifba.inf008.domain.Cart;
import br.edu.ifba.inf008.domain.CartItem;
import br.edu.ifba.inf008.domain.Customer;
import br.edu.ifba.inf008.domain.Product;
import br.edu.ifba.inf008.domain.StockMovement;

import br.edu.ifba.inf008.interfaces.core.ICore;
import br.edu.ifba.inf008.interfaces.core.IPersistenceController;
import br.edu.ifba.inf008.interfaces.core.IPluginRegistrar;

import br.edu.ifba.inf008.interfaces.plugins.IPlugin;
import br.edu.ifba.inf008.interfaces.plugins.ICartService;
import br.edu.ifba.inf008.interfaces.plugins.ICatalogService;

import br.edu.ifba.inf008.interfaces.exceptions.InsufficientStockException;
import br.edu.ifba.inf008.interfaces.exceptions.NotFoundException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CartPlugin implements IPlugin, ICartService {
    private IPersistenceController persistenceController;
    private IPluginRegistrar pluginRegistrar;

    private Cart cart = null;
    private Map<CartItem, StockMovement> cartStockMovements = new HashMap<CartItem, StockMovement>();

    private Customer testCustomer;
    
    public boolean init() {
        pluginRegistrar =  ICore.getInstance().getPluginRegistrar();

        pluginRegistrar.register(IPlugin.class, this);
        pluginRegistrar.register(ICartService.class, this);
        return true ;
    } 

    public void start () {
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
                () -> toCheckout(),
                this::updateCartItemQuantity
            );
            updateView();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void cancelCart () {
        CartView.cancelCartTab();

        if (!cartStockMovements.isEmpty()) {
            cartStockMovements.forEach((cartItem, stockMovement) -> {
                persistenceController.delete(StockMovement.class, stockMovement.getId());
                persistenceController.delete(CartItem.class, cartItem.getId());
            });
        }
        
        persistenceController.delete(cart.getClass(), cart.getId());
        cart = null;
    }

    public void addCartItem (long productId) {
        try {
            if (cart == null)
                throw new NotFoundException("You haven't a shipping cart already.");

            for (CartItem ci : cart.getItems()) {
                if (ci.getProduct().getId().equals(productId)) {
                    updateCartItemQuantity(ci, 1);
                    return;
                }
            }

            Product p = persistenceController.findById(Product.class, Long.valueOf(productId));

            checkStock(p, 1);

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

    public void addCartItems(List<Long> productIDs) {
        for (Long productId : productIDs) {
            addCartItem(productId);
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
                    item.getProduct(),
                    change + item.getQuantity()
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

    public void toCheckout () {

    }

    private void checkStock (Product product, int change) {
        Object service = pluginRegistrar.getPlugin(ICatalogService.class);
        ICatalogService catalogService = null;

        if (service instanceof ICatalogService) {
            catalogService = (ICatalogService) service;
            catalogService.checkStock(product, change);
        }
    }

    private void updateView () {
        CartView.updateTable(cart);
        CartView.updateSummary(() -> getItemsCount(), () -> getSubtotal());
    }
}