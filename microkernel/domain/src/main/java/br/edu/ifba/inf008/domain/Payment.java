package br.edu.ifba.inf008.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Setter
    @Column(name = "payment_method", nullable = false, length = 40)
    private String paymentMethod;

    @Setter
    @Column(nullable = false, length = 30)
    private String status;

    @Setter
    @Column(nullable = false)
    private BigDecimal amount;

    @Setter
    @Column(name = "transaction_reference", length = 80)
    private String transactionReference;

    @Setter
    @Column(name = "failure_reason", length = 160)
    private String failureReason;

    @Setter
    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    public Payment(Order order, String paymentMethod, String status, BigDecimal amount) {
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.amount = amount;
    }
}