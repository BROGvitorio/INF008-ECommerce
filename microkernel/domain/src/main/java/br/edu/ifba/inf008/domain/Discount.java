package br.edu.ifba.inf008.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 40)
    private String code;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(name = "discount_type", nullable = false, length = 30)
    private String discountType;

    @Column(nullable = false)
    private BigDecimal value;

    @Column(nullable = false)
    private Boolean active = true;

    public Discount() {}

    public Discount(String code, String name, String discountType,
                    BigDecimal value, Boolean active) {
        this.code = code;
        this.name = name;
        this.discountType = discountType;
        this.value = value;
        this.active = active;
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

    public String getDiscountType() {
        return discountType;
    }
    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getValue() {
        return value;
    }
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
}