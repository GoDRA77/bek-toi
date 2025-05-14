package com.example.demo.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;              // Название мероприятия
    private String location;           // Место проведения
    private LocalDateTime eventTime;   // Время
    private String eventType;          // Тип (свадьба, торжество и т.д.)
    private BigDecimal totalPrice;     // Общая стоимость услуг

    public Event() {}

    public Event(String title, String location, LocalDateTime eventTime,
                 String eventType, BigDecimal totalPrice) {
        this.title = title;
        this.location = location;
        this.eventTime = eventTime;
        this.eventType = eventType;
        this.totalPrice = totalPrice;
    }

    // Геттеры и сеттеры
    // ...
}
