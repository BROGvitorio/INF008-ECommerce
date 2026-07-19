package br.edu.ifba.inf008.plugins;

import java.util.List;

import java.math.BigDecimal;
import java.util.function.Function;

import br.edu.ifba.inf008.domain.Cart;
import br.edu.ifba.inf008.domain.CartItem;
import br.edu.ifba.inf008.domain.Customer;
import br.edu.ifba.inf008.domain.Product;

import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.ICheckoutComponent;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IPersistenceController;
import br.edu.ifba.inf008.interfaces.IPluginRegistry;

import javafx.scene.layout.VBox;

public class CheckoutPlugin implements IPlugin {
    private IPersistenceController persistenceController = null;
    private IPluginRegistry pluginRegistry = null;

    private Cart cart;

    public boolean init() {
        persistenceController = ICore.getInstance().getPersistenceController();
        pluginRegistry = ICore.getInstance().getPluginRegistry();
        return true;
    }
    
    public void start() {
        Cart lastCart = null;
        for (Cart c : persistenceController.findAll(Cart.class)) {
            if (lastCart == null || c.getId() > lastCart.getId()) {
                lastCart = c;
            }
        }
        cart = lastCart;
    
        if (cart == null) {
            System.out.println("It wasn't possible to load the last shopping cart.");
        }
    
        CheckoutView.createCheckoutPage(
            cart, 
            () -> getItemsCount(),
            () -> getItemsTotal(),
            this::getComponentUIByName
        );
    }

    public int getItemsCount () {
        int sum = 0;

        for (CartItem ci : cart.getItems()) {
            sum += ci.getQuantity();
        }
        
        return sum;
    }

    public BigDecimal getItemsTotal () {
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

    public VBox getComponentUIByName (String name) {
        ICheckoutComponent component = null;

        for (Object obj : pluginRegistry.getPlugins(ICheckoutComponent.class)) {
            if (obj instanceof ICheckoutComponent) {
                component = (ICheckoutComponent) obj;

                if (component.getName().equalsIgnoreCase(name))
                    return component.getUI();
            }
        }

        return null;
    }
}
