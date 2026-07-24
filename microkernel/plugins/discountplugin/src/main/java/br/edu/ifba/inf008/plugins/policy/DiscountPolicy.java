package br.edu.ifba.inf008.plugins.policy;

import br.edu.ifba.inf008.domain.Order;

import java.math.BigDecimal;

public interface DiscountPolicy {
    BigDecimal calculateDiscount(Order order, Discount discount);
}