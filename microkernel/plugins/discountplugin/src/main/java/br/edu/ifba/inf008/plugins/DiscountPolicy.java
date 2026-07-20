package br.edu.ifba.inf008.plugins;

public interface DiscountPolicy {
    String getName();

    Double calculateDiscount(Order order);
}