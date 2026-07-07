package br.edu.ifba.inf008.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderDiscountId implements Serializable {
    private Long orderId;
    private Long discountId;

    public OrderDiscountId() {}

    public OrderDiscountId(Long orderId, Long discountId) {
        this.orderId = orderId;
        this.discountId = discountId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getDiscountId() {
        return discountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDiscountId)) return false;
        OrderDiscountId that = (OrderDiscountId) o;
        return Objects.equals(orderId, that.orderId)
                && Objects.equals(discountId, that.discountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, discountId);
    }
}