package br.edu.ifba.inf008.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "shipping_methods")
public class ShippingMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 40)
    private String code;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(name = "base_cost", nullable = false)
    private BigDecimal baseCost;

    @Column(name = "estimated_days", nullable = false)
    private Integer estimatedDays;

    @Column(name = "free_shipping_threshold")
    private BigDecimal freeShippingThreshold;

    public ShippingMethod() {}

    public ShippingMethod(String code, String name, BigDecimal baseCost, Integer estimatedDays, 
                          BigDecimal freeShippingThreshold) {
        this.code = code;
        this.name = name;
        this.baseCost = baseCost;
        this.estimatedDays = estimatedDays;
        this.freeShippingThreshold = freeShippingThreshold;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBaseCost() {
        return baseCost;
    }
    public void setBaseCost(BigDecimal baseCost) {
        this.baseCost = baseCost;
    }

    public Integer getEstimatedDays() {
        return estimatedDays;
    }
    public void setEstimatedDays(Integer estimatedDays) {
        this.estimatedDays = estimatedDays;
    }

    public BigDecimal getFreeShippingThreshold() {
        return freeShippingThreshold;
    }
    public void setFreeShippingThreshold(BigDecimal freeShippingThreshold) {
        this.freeShippingThreshold = freeShippingThreshold;
    }
}