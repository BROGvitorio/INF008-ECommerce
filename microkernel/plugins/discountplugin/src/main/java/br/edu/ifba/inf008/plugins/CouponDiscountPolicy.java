package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.domain.Order;

import java.math.BigDecimal;

public class CouponDiscountPolicy implements DiscountPolicy {
    public String getName() {
        return "Coupon";
    }

    public BigDecimal calculateDiscount(Order order) {
        return order.getSubtotal().multiply(BigDecimal.valueOf(0.10));
    }
}