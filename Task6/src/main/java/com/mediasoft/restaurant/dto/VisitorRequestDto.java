package com.mediasoft.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitorRequestDto {
    private String name; // может быть null для анонимных отзывов
    private Integer age;
    private String gender;
} 