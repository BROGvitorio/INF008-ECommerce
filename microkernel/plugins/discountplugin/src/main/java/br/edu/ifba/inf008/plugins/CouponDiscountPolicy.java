package br.edu.ifba.inf008.plugins;

public class CouponDiscountPolicy implements DiscountPolicy {
    public String getName() {
        return "Coupon";
    }

    public Double calculateDiscount(Order order) {
        return order.getSubtotal() * 0.10;
    }
}