package br.edu.ifba.inf008.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "carts")
@Getter
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Setter
    @Column(nullable = false, length = 30)
    private String status;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> items = new ArrayList<CartItem>();

    public Cart(Customer customer, String status) {
        this.customer = customer;
        this.status = status;
    }

    public List<CartItem> getItems () {
        return items;
    }

    public void addItem (CartItem item) {
        items.add(item);
    }

    public void removeItem (CartItem item) {
        items.add(item);
    }
}