package br.edu.ifba.inf008.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "discounts")
@Getter
@NoArgsConstructor
public class Discount {
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
    @Column(name = "discount_type", nullable = false, length = 30)
    private String discountType;

    @Setter
    @Column(nullable = false)
    private BigDecimal value;

    @Setter
    @Column(nullable = false)
    private Boolean active = true;

    public Discount(String code, String name, String discountType,
                    BigDecimal value, Boolean active) {
        this.code = code;
        this.name = name;
        this.discountType = discountType;
        this.value = value;
        this.active = active;
    }
}