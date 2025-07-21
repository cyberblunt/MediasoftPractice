package com.mediasoft.restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ratings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitor_id", nullable = false)
    private Visitor visitor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
    
    @Column(name = "score", nullable = false)
    private int score;
    
    @Column(name = "review", columnDefinition = "TEXT")
    private String review; // может быть пустым
    
    // Конструктор для совместимости с существующим кодом
    public Rating(Long id, Long visitorId, Long restaurantId, int score, String review) {
        this.id = id;
        this.score = score;
        this.review = review;
        // Создаем временные объекты для совместимости
        this.visitor = new Visitor();
        this.visitor.setId(visitorId);
        this.restaurant = new Restaurant();
        this.restaurant.setId(restaurantId);
    }
    
    // Геттеры для совместимости с существующим кодом
    public Long getVisitorId() {
        return visitor != null ? visitor.getId() : null;
    }
    
    public Long getRestaurantId() {
        return restaurant != null ? restaurant.getId() : null;
    }
} 