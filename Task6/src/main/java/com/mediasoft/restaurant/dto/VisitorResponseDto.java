package com.mediasoft.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitorResponseDto {
    private Long id;
    private String name;
    private Integer age;
    private String gender;
} 