package com.mediasoft.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    private Long id;
    private String name;
    private String description; // может быть пустым
    private CuisineType cuisineType;
    private BigDecimal averageCheck;
    private BigDecimal rating; // оценка пользователей
} 