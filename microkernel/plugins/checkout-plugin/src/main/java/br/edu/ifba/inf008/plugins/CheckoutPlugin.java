package br.edu.ifba.inf008.plugins;


import java.math.BigDecimal;

import br.edu.ifba.inf008.domain.Cart;
import br.edu.ifba.inf008.domain.CartItem;
import br.edu.ifba.inf008.domain.Customer;
import br.edu.ifba.inf008.domain.Product;


import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IPersistenceController;

public class CheckoutPlugin implements IPlugin {
    private IPersistenceController persistenceController = null;

    private Cart testCart;
    private Customer testCustomer;

    private void loadMockData () {
        testCustomer = persistenceController.findById(Customer.class, Long.valueOf(5));
        if (testCustomer == null) {
            testCustomer = new Customer("Checkout Test Customer", "checkout.test.customer@email.com", "STUDENT");
            persistenceController.save(testCustomer);
        }

        testCart = persistenceController.findById(Cart.class, Long.valueOf(4));
        if (testCart == null) {
            testCart = new Cart(testCustomer, "OPEN");
            persistenceController.save(testCart);
        }

        Product p1 = persistenceController.findById(Product.class, Long.valueOf(1));
        Product p2 = persistenceController.findById(Product.class, Long.valueOf(2));
        Product p3 = persistenceController.findById(Product.class, Long.valueOf(3));
        Product p4 = persistenceController.findById(Product.class, Long.valueOf(4));

        testCart.addItem(
            new CartItem (
                testCart,
                p1,
                Integer.valueOf(1),
                p1.getUnitPrice()
            )
        );

        testCart.addItem(
            new CartItem (
                testCart,
                p2,
                Integer.valueOf(2),
                p2.getUnitPrice()
            )
        );
        testCart.addItem(
            new CartItem (
                testCart,
                p3,
                Integer.valueOf(3),
                p3.getUnitPrice()
            )
        );
        testCart.addItem(
            new CartItem (
                testCart,
                p4,
                Integer.valueOf(4),
                p4.getUnitPrice()
            )
        );
    }

    public boolean init() {
        persistenceController = ICore.getInstance().getPersistenceController();
        
        loadMockData();

        CheckoutView.createCheckoutPage(
            testCart, 
            () -> getItemsCount(),
            () -> getItemsTotal()
        );
        

        return true;
    }

    public int getItemsCount () {
        int sum = 0;

        for (CartItem ci : testCart.getItems()) {
            sum += ci.getQuantity();
        }
        
        return sum;
    }

    public BigDecimal getItemsTotal () {
        BigDecimal subtotal = new BigDecimal(0);

        for (CartItem ci : testCart.getItems()) {
            subtotal = subtotal.add(
                ci
                .getUnitPrice()
                .multiply(BigDecimal.valueOf(ci.getQuantity()))
            );
        }

        return subtotal;
    }
}
