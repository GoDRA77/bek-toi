package com.example.demo.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "supplier_requests")
public class SupplierRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long supplierId;
    private String name;
    private String type;
    private String description;
    private String contactInfo;
    private BigDecimal price;
    private boolean approved;

    // Конструктор без аргументов
    public SupplierRequest() {}

    // Геттеры
    public Long getId() {
        return id;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isApproved() {
        return approved;
    }

    // Сеттеры
    public void setId(Long id) {
        this.id = id;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
