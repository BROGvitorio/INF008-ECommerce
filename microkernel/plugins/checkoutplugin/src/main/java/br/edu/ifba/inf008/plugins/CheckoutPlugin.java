package br.edu.ifba.inf008.plugins;

import java.util.List;

import java.math.BigDecimal;
import java.util.function.Function;

import br.edu.ifba.inf008.domain.Cart;
import br.edu.ifba.inf008.domain.Order;
import br.edu.ifba.inf008.domain.CartItem;
import br.edu.ifba.inf008.domain.Customer;
import br.edu.ifba.inf008.domain.Product;

import br.edu.ifba.inf008.interfaces.plugins.IPlugin;
import br.edu.ifba.inf008.interfaces.plugins.ICheckoutComponent;
import br.edu.ifba.inf008.interfaces.plugins.ICheckoutService;
import br.edu.ifba.inf008.interfaces.plugins.CheckoutContext;
import br.edu.ifba.inf008.interfaces.core.ICore;
import br.edu.ifba.inf008.interfaces.core.IPersistenceController;
import br.edu.ifba.inf008.interfaces.core.IPluginRegistry;

import javafx.scene.layout.VBox;

public class CheckoutPlugin implements IPlugin, ICheckoutService {
    private IPersistenceController persistenceController = null;
    private IPluginRegistry pluginRegistry = null;

    private Customer customer;
    private Cart cart;
    private Order order;

    public boolean init() {
        pluginRegistry = ICore.getInstance().getPluginRegistry();
        pluginRegistry.register(IPlugin.class, this);
        pluginRegistry.register(ICheckoutService.class, this);
        
        return true;
    }
    
    public void start() {
        persistenceController = ICore.getInstance().getPersistenceController();

        try {
            customer = ICore.getInstance().getAuthenticationController().signIn();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    // public void setCart(Cart cart) {
    //     this.cart = cart;
        
    //     if (cart == null) {
    //         System.out.println("It wasn't possible to load the shopping cart.");
    //         return;
    //     }
        
    //     CheckoutView.createCheckoutPage(
    //         cart, 
    //         () -> getItemsCount(),
    //         () -> getItemsTotal(),
    //         this::getComponentUIByName,
    //         () -> checkout()
    //     );
    // }

    public void createOrder (Cart cart) {
        this.cart = cart;
        
        if (cart == null) {
            System.out.println("It wasn't possible to load the shopping cart.");
            return;
        }

        order = new Order(
            customer,
            cart,
            "PENDING_PAYMENT",
            getItemsTotal()
        );

        CheckoutView.createCheckoutPage(
            cart, 
            () -> getItemsCount(),
            () -> getItemsTotal(),
            this::getComponentUIByName,
            () -> checkout()
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

        List<Object> components = pluginRegistry.getPlugins(ICheckoutComponent.class);
        if (components.isEmpty()) {
            System.out.println("No checkout components are currently available.");
            return new VBox();
        }

        for (Object obj : components) {
            if (obj instanceof ICheckoutComponent) {
                component = (ICheckoutComponent) obj;

                if (component.getName().equalsIgnoreCase(name))
                    return component.getUI();
            }
        }

        return new VBox();
    }

    public void checkout() {
        CheckoutContext context = new CheckoutContext(order);
        ICheckoutComponent component = null;
        
        try {
            List<Object> components = pluginRegistry.getPlugins(ICheckoutComponent.class);
            if (components.isEmpty()) {
                System.out.println("No checkout components are currently available.");
                return;
            }

            for (Object obj : components) {
                if (obj instanceof ICheckoutComponent) {
                    component = (ICheckoutComponent) obj;

                    component.process();
                }
            }

            order.setStatus(context.getPaymentStatus());
            order.setShippingMethod(context.getShippingMethod());
            order.setShippingTotal(context.getShippingTotal());
            order.setDiscountTotal(context.getDiscountTotal());

            persistenceController.save(order);
            
            CheckoutView.showErrorMessage("");
            CheckoutView.showOrderConfirmedPopup();
        } catch (Exception e) {
            CheckoutView.showErrorMessage(e.getMessage());
        }
    }
}