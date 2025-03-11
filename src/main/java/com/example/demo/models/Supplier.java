package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Название поставщика

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SupplierType type; // Тип поставщика (банкетный зал, актер и т.д.)

    private String description; // Описание услуг

    private String contactInfo; // Контактные данные (телефон, email и т.д.)
}
