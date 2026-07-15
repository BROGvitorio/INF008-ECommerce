package br.edu.ifba.inf008.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "order_discounts")
@Getter
@NoArgsConstructor
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

    @Setter
    @Column(nullable = false)
    private BigDecimal amount;

    public OrderDiscount(Order order, Discount discount, BigDecimal amount) {
        this.order = order;
        this.discount = discount;
        this.amount = amount;
        this.id = new OrderDiscountId(order.getId(), discount.getId());
    }
}