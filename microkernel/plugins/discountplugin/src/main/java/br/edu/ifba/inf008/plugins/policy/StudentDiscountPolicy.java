package br.edu.ifba.inf008.plugins.policy;

import br.edu.ifba.inf008.domain.Order;

import java.math.BigDecimal;

public class StudentDiscountPolicy implements DiscountPolicy {
    public BigDecimal calculateDiscount(Order order, Discount discount) {
        Customer customer = order.getCustomer();

        if (!customer.getCustomerType().equals("STUDENT")) {
            return BigDecimal.ZERO;
        }

        return DiscountCalculator.calculate(order, discount);
    }
}