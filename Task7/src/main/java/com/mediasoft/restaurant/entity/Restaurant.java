package com.mediasoft.restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // может быть пустым
    
    @Enumerated(EnumType.STRING)
    @Column(name = "cuisine_type", nullable = false)
    private CuisineType cuisineType;
    
    @Column(name = "average_check", nullable = false, precision = 10, scale = 2)
    private BigDecimal averageCheck;
    
    @Column(name = "rating", precision = 3, scale = 2)
    private BigDecimal rating; // оценка пользователей
    
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rating> ratings;
    
    // Конструктор для совместимости с существующим кодом
    public Restaurant(Long id, String name, String description, CuisineType cuisineType, BigDecimal averageCheck, BigDecimal rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cuisineType = cuisineType;
        this.averageCheck = averageCheck;
        this.rating = rating;
    }
} 