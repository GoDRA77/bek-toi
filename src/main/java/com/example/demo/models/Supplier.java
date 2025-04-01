package com.example.demo.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "suppliers")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String description;
    private String contactInfo;
    private BigDecimal price;

    // Пустой конструктор (обязателен для JPA)
    public Supplier() {}

    // Конструктор со всеми полями
    public Supplier(Long id, String name, String type, String description, String contactInfo, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.contactInfo = contactInfo;
        this.price = price;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
