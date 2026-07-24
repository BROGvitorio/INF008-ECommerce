package br.edu.ifba.inf008.plugins.policy;

import br.edu.ifba.inf008.domain.Order;

import java.math.BigDecimal;

public class CouponDiscountPolicy implements DiscountPolicy {
    public BigDecimal calculateDiscount(Order order, Discount discount) {
        return DiscountCalculator.calculate(order, discount);
    }
}