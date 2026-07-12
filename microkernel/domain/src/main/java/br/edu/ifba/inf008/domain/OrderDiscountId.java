package br.edu.ifba.inf008.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
public class OrderDiscountId implements Serializable {
    private Long orderId;
    private Long discountId;

    public OrderDiscountId(Long orderId, Long discountId) {
        this.orderId = orderId;
        this.discountId = discountId;
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