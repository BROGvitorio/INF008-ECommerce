package br.edu.ifba.inf008.plugins;

public class StudentDiscountPolicy implements DiscountPolicy {
    public String getName() {
        return "Student Discount";
    }

    public Double calculateDiscount(Order order) {
        return order.getSubtotal() * 0.15;
    }
}