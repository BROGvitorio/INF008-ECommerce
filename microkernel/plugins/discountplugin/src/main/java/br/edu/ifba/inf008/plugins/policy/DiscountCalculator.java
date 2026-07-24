package br.edu.ifba.inf008.plugins.policy;

import br.edu.ifba.inf008.domain.Order;

import java.math.BigDecimal;

public final class DiscountCalculator {
    public static BigDecimal calculate(Order order, Discount discount) {
        if (discount.getDiscountType().equals("PERCENTAGE")) {
            return order.getSubtotal()
                .multiply(discount.getValue())
                .divide(BigDecimal.valueOf(100));
        }

        if (discount.getDiscountType().equals("FIXED_AMOUNT")) {
            return discount.getValue();
        }

        return BigDecimal.ZERO;
    }
}