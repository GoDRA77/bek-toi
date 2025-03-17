package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Supplier> suppliers; // Список выбранных услуг

    @Getter
    @Column(nullable = false)
    private BigDecimal totalPrice = BigDecimal.ZERO; // Общая стоимость

    @Column(nullable = false, unique = true)
    private Long userId; // ID пользователя (каждый пользователь имеет 1 корзину)

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
