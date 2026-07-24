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
     public Order getOrder() {
        return order;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }
    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public BigDecimal getShippingTotal() {
        return shippingTotal;
    }
    public void setShippingTotal(BigDecimal shippingTotal) {
        this.shippingTotal = shippingTotal;
    }

    public BigDecimal getDiscountTotal() {
        return discountTotal;
    }
    public void setDiscountTotal(BigDecimal discountTotal) {
        this.discountTotal = discountTotal;
    }
}
