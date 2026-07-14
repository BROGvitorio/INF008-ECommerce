package br.edu.ifba.inf008.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true, length = 40)
    private String sku;

    @Setter
    @Column(nullable = false, length = 120)
    private String name;

    @Setter
    @Column(nullable = false, length = 255)
    private String description;

    @Setter
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Setter
    @Column(nullable = false)
    private Boolean active = true;

    public Product(String sku, String name, String description, BigDecimal unitPrice) {
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.active = true;
    }
}