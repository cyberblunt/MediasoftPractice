package com.mediasoft.restaurant;

import com.mediasoft.restaurant.entity.CuisineType;
import com.mediasoft.restaurant.entity.Rating;
import com.mediasoft.restaurant.entity.Restaurant;
import com.mediasoft.restaurant.entity.Visitor;
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
        Visitor newVisitor = new Visitor(null, "Мария", 27, "Женский");
        Visitor savedVisitor = visitorService.save(newVisitor);
        System.out.println("Добавлен посетитель: " + savedVisitor.getName() + " с ID: " + savedVisitor.getId());
        
        // Тестируем добавление нового ресторана
        System.out.println("\n2. Добавляем новый ресторан:");
        Restaurant newRestaurant = new Restaurant(null, "Китайский ресторан", 
                "Традиционная китайская кухня", CuisineType.CHINESE, 
                new BigDecimal("1200.00"), new BigDecimal("0.00"));
        Restaurant savedRestaurant = restaurantService.save(newRestaurant);
        System.out.println("Добавлен ресторан: " + savedRestaurant.getName() + " с ID: " + savedRestaurant.getId());
        
        // Тестируем добавление оценки
        System.out.println("\n3. Добавляем оценку:");
        Rating newRating = new Rating(null, savedVisitor.getId(), savedRestaurant.getId(), 5, 
                "Отличная китайская кухня!");
        Rating savedRating = ratingService.save(newRating);
        System.out.println("Добавлена оценка с ID: " + savedRating.getId() + 
                ", Оценка: " + savedRating.getScore());
        
        // Проверяем, что рейтинг ресторана обновился
        System.out.println("\n4. Проверяем обновленный рейтинг ресторана:");
        Restaurant updatedRestaurant = restaurantService.findAll().stream()
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
        ratingService.remove(savedRating.getId());
        System.out.println("Количество оценок после удаления: " + ratingService.findAll().size());
        
        // Проверяем, что рейтинг ресторана сбросился
        Restaurant resetRestaurant = restaurantService.findAll().stream()
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