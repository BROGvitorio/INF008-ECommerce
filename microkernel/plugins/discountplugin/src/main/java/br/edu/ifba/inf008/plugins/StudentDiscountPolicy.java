package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.domain.Order;

import java.math.BigDecimal;

public class StudentDiscountPolicy implements DiscountPolicy {
    public String getName() {
        return "Student Discount";
    }

    public BigDecimal calculateDiscount(Order order) {
        return order.getSubtotal().multiply(BigDecimal.valueOf(0.15));
    }
}