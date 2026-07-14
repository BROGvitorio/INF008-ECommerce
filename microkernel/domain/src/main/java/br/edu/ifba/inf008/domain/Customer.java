package br.edu.ifba.inf008.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Getter
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "full_name", nullable = false, length = 120)
    private String fullName;

    @Setter
    @Column(nullable = false, unique = true, length = 160)
    private String email;

    @Setter
    @Column(name = "customer_type", nullable = false, length = 30)
    private String customerType;
    
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public Customer(String fullName, String email, String customerType) {
        this.fullName = fullName;
        this.email = email;
        this.customerType = customerType;
    }
}