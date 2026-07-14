package br.edu.ifba.inf008.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Setter
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Setter
    @ManyToOne
    @JoinColumn(name = "shipping_method_id", nullable = false)
    private ShippingMethod shippingMethod;

    @Setter
    @Column(nullable = false, length = 30)
    private String status;

    @Setter
    @Column(nullable = false)
    private BigDecimal subtotal;

    @Setter
    @Column(name = "discount_total", nullable = false)
    private BigDecimal discountTotal;

    @Setter
    @Column(name = "shipping_total", nullable = false)
    private BigDecimal shippingTotal;

    @Setter
    @Column(name = "grand_total", nullable = false)
    private BigDecimal grandTotal;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public Order(Customer customer, Cart cart, ShippingMethod shippingMethod, String status, 
                 BigDecimal subtotal, BigDecimal discountTotal, BigDecimal shippingTotal,
                 BigDecimal grandTotal) {
        this.customer = customer;
        this.cart = cart;
        this.shippingMethod = shippingMethod;
        this.status = status;
        this.subtotal = subtotal;
        this.discountTotal = discountTotal;
        this.shippingTotal = shippingTotal;
        this.grandTotal = grandTotal;
    }
}