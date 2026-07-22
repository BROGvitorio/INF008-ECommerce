package br.edu.ifba.inf008.plugins;

import br.edu.ifba.inf008.domain.Order;

import java.math.BigDecimal;

public interface DiscountPolicy {
    String getName();

    BigDecimal calculateDiscount(Order order);
}