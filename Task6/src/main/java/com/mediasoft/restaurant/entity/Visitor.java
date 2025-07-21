package com.mediasoft.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visitor {
    private Long id;
    private String name; // может быть null (анонимный отзыв)
    private int age;
    private String gender;
} 