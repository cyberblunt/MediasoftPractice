package com.mediasoft.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    private Long id;
    private Long visitorId;
    private Long restaurantId;
    private int score;
    private String review; // может быть пустым
} 