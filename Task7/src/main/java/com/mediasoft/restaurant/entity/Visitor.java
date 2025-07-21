package com.mediasoft.restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "visitors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name; // может быть null (анонимный отзыв)
    
    @Column(name = "age", nullable = false)
    private int age;
    
    @Column(name = "gender", nullable = false)
    private String gender;
    
    @OneToMany(mappedBy = "visitor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rating> ratings;
    
    // Конструктор для совместимости с существующим кодом
    public Visitor(Long id, String name, int age, String gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
} 