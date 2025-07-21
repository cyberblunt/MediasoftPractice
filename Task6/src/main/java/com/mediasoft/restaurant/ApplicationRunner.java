package com.mediasoft.restaurant;

import com.mediasoft.restaurant.dto.RatingRequestDto;
import com.mediasoft.restaurant.dto.RestaurantRequestDto;
import com.mediasoft.restaurant.dto.VisitorRequestDto;
import com.mediasoft.restaurant.entity.CuisineType;
import com.mediasoft.restaurant.service.RatingService;
import com.mediasoft.restaurant.service.RestaurantService;
import com.mediasoft.restaurant.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ApplicationRunner implements CommandLineRunner {
    
    @Autowired
    private VisitorService visitorService;
    
    @Autowired
    private RestaurantService restaurantService;
    
    @Autowired
    private RatingService ratingService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n=== Тестирование через CommandLineRunner ===");
        
        // Тестируем добавление нового посетителя
        System.out.println("\n1. Добавляем нового посетителя:");
        VisitorRequestDto newVisitorDto = new VisitorRequestDto("Мария", 27, "Женский");
        var savedVisitor = visitorService.save(newVisitorDto);
        System.out.println("Добавлен посетитель: " + savedVisitor.getName() + " с ID: " + savedVisitor.getId());
        
        // Тестируем добавление нового ресторана
        System.out.println("\n2. Добавляем новый ресторан:");
        RestaurantRequestDto newRestaurantDto = new RestaurantRequestDto("Китайский ресторан", 
                "Традиционная китайская кухня", CuisineType.CHINESE, 
                new BigDecimal("1200.00"));
        var savedRestaurant = restaurantService.save(newRestaurantDto);
        System.out.println("Добавлен ресторан: " + savedRestaurant.getName() + " с ID: " + savedRestaurant.getId());
        
        // Тестируем добавление оценки
        System.out.println("\n3. Добавляем оценку:");
        RatingRequestDto newRatingDto = new RatingRequestDto(savedVisitor.getId(), savedRestaurant.getId(), 5, 
                "Отличная китайская кухня!");
        var savedRating = ratingService.save(newRatingDto);
        System.out.println("Добавлена оценка с ID: " + savedRating.getId() + 
                ", Оценка: " + savedRating.getScore());
        
        // Проверяем, что рейтинг ресторана обновился
        System.out.println("\n4. Проверяем обновленный рейтинг ресторана:");
        var updatedRestaurant = restaurantService.findAll().stream()
                .filter(r -> r.getId().equals(savedRestaurant.getId()))
                .findFirst()
                .orElse(null);
        if (updatedRestaurant != null) {
            System.out.println("Ресторан: " + updatedRestaurant.getName() + 
                    ", Новый рейтинг: " + updatedRestaurant.getRating());
        }
        
        // Тестируем удаление
        System.out.println("\n5. Тестируем удаление оценки:");
        System.out.println("Количество оценок до удаления: " + ratingService.findAll().size());
        ratingService.delete(savedRating.getId());
        System.out.println("Количество оценок после удаления: " + ratingService.findAll().size());
        
        // Проверяем, что рейтинг ресторана сбросился
        var resetRestaurant = restaurantService.findAll().stream()
                .filter(r -> r.getId().equals(savedRestaurant.getId()))
                .findFirst()
                .orElse(null);
        if (resetRestaurant != null) {
            System.out.println("Ресторан: " + resetRestaurant.getName() + 
                    ", Рейтинг после удаления оценки: " + resetRestaurant.getRating());
        }
        
        System.out.println("\n=== Тестирование завершено ===");
    }
} 