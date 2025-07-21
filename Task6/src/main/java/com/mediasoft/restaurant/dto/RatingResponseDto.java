package com.mediasoft.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponseDto {
    private Long id;
    private Long visitorId;
    private Long restaurantId;
    private Integer score;
    private String review;
} 