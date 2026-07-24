package br.edu.ifba.inf008.interfaces.plugins;

import java.math.BigDecimal;

import br.edu.ifba.inf008.domain.Order;
import br.edu.ifba.inf008.domain.ShippingMethod;

public class CheckoutContext {
    private Order order;

    private String paymentStatus;
    private ShippingMethod shippingMethod;
    private BigDecimal shippingTotal = BigDecimal.ZERO;
    private BigDecimal discountTotal = BigDecimal.ZERO;

    public CheckoutContext(Order order){
        this.order = order;
    }

}
