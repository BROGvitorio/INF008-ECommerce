package br.edu.ifba.inf008.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "shipping_methods")
@Getter
@NoArgsConstructor
public class ShippingMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 40)
    private String code;

    @Setter
    @Column(nullable = false, length = 80)
    private String name;

    @Setter
    @Column(name = "base_cost", nullable = false)
    private BigDecimal baseCost;

    @Setter
    @Column(name = "estimated_days", nullable = false)
    private Integer estimatedDays;

    @Setter
    @Column(name = "free_shipping_threshold")
    private BigDecimal freeShippingThreshold;

    public ShippingMethod(String code, String name, BigDecimal baseCost, Integer estimatedDays, 
                          BigDecimal freeShippingThreshold) {
        this.code = code;
        this.name = name;
        this.baseCost = baseCost;
        this.estimatedDays = estimatedDays;
        this.freeShippingThreshold = freeShippingThreshold;
    }
}