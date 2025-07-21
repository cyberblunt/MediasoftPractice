package com.mediasoft.restaurant.dto;

import com.mediasoft.restaurant.entity.CuisineType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequestDto {
    private String name;
    private String description; // может быть пустым
    private CuisineType cuisineType;
    private BigDecimal averageCheck;
} 