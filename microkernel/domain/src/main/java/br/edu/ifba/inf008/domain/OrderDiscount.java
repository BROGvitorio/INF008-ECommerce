package br.edu.ifba.inf008.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_discounts")
public class OrderDiscount {
    @EmbeddedId
    private OrderDiscountId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("discountId")
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @Column(nullable = false)
    private BigDecimal amount;

    public OrderDiscount() {}

    public OrderDiscount(Order order, Discount discount, BigDecimal amount) {
        this.order = order;
        this.discount = discount;
        this.amount = amount;
        this.id = new OrderDiscountId(order.getId(), discount.getId());
    }

    public OrderDiscountId getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Discount getDiscount() {
        return discount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}