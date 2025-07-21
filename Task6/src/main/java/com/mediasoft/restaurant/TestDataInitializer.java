package com.mediasoft.restaurant;

import com.mediasoft.restaurant.dto.RatingRequestDto;
import com.mediasoft.restaurant.dto.RestaurantRequestDto;
import com.mediasoft.restaurant.dto.VisitorRequestDto;
import com.mediasoft.restaurant.entity.CuisineType;
import com.mediasoft.restaurant.service.RatingService;
import com.mediasoft.restaurant.service.RestaurantService;
import com.mediasoft.restaurant.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
public class TestDataInitializer {
    
    @Autowired
    private VisitorService visitorService;
    
    @Autowired
    private RestaurantService restaurantService;
    
    @Autowired
    private RatingService ratingService;

    @PostConstruct
    public void initializeTestData() {
        System.out.println("=== Инициализация тестовых данных ===");
        
        // Создаем посетителей
        VisitorRequestDto visitor1Dto = new VisitorRequestDto("Анна", 25, "Женский");
        VisitorRequestDto visitor2Dto = new VisitorRequestDto("Иван", 30, "Мужской");
        VisitorRequestDto visitor3Dto = new VisitorRequestDto(null, 28, "Женский"); // анонимный посетитель
        
        var savedVisitor1 = visitorService.save(visitor1Dto);
        var savedVisitor2 = visitorService.save(visitor2Dto);
        var savedVisitor3 = visitorService.save(visitor3Dto);
        
        System.out.println("Создано посетителей: " + visitorService.findAll().size());
        
        // Создаем рестораны
        RestaurantRequestDto restaurant1Dto = new RestaurantRequestDto("Итальянский дворик", 
                "Аутентичная итальянская кухня", CuisineType.ITALIAN, 
                new BigDecimal("1500.00"));
        
        RestaurantRequestDto restaurant2Dto = new RestaurantRequestDto("Суши-бар Сакура", 
                "Японская кухня и суши", CuisineType.JAPANESE, 
                new BigDecimal("2000.00"));
        
        RestaurantRequestDto restaurant3Dto = new RestaurantRequestDto("Европейский ресторан", 
                "", CuisineType.EUROPEAN, 
                new BigDecimal("1800.00"));
        
        var savedRestaurant1 = restaurantService.save(restaurant1Dto);
        var savedRestaurant2 = restaurantService.save(restaurant2Dto);
        var savedRestaurant3 = restaurantService.save(restaurant3Dto);
        
        System.out.println("Создано ресторанов: " + restaurantService.findAll().size());
        
        // Создаем оценки
        RatingRequestDto rating1Dto = new RatingRequestDto(savedVisitor1.getId(), savedRestaurant1.getId(), 5, 
                "Отличная паста и атмосфера!");
        RatingRequestDto rating2Dto = new RatingRequestDto(savedVisitor2.getId(), savedRestaurant1.getId(), 4, 
                "Хорошая кухня, но дорого");
        RatingRequestDto rating3Dto = new RatingRequestDto(savedVisitor3.getId(), savedRestaurant1.getId(), 5, 
                "Вкусно!");
        
        RatingRequestDto rating4Dto = new RatingRequestDto(savedVisitor1.getId(), savedRestaurant2.getId(), 3, 
                "Неплохо, но могло быть лучше");
        RatingRequestDto rating5Dto = new RatingRequestDto(savedVisitor2.getId(), savedRestaurant2.getId(), 5, 
                "Лучшие суши в городе!");
        
        RatingRequestDto rating6Dto = new RatingRequestDto(savedVisitor1.getId(), savedRestaurant3.getId(), 4, 
                "Классическая европейская кухня");
        
        ratingService.save(rating1Dto);
        ratingService.save(rating2Dto);
        ratingService.save(rating3Dto);
        ratingService.save(rating4Dto);
        ratingService.save(rating5Dto);
        ratingService.save(rating6Dto);
        
        System.out.println("Создано оценок: " + ratingService.findAll().size());
        
        // Выводим результаты
        System.out.println("\n=== Результаты ===");
        System.out.println("Посетители:");
        visitorService.findAll().forEach(v -> 
            System.out.println("  ID: " + v.getId() + ", Имя: " + 
                (v.getName() != null ? v.getName() : "Аноним") + 
                ", Возраст: " + v.getAge() + ", Пол: " + v.getGender()));
        
        System.out.println("\nРестораны:");
        restaurantService.findAll().stream()
            .distinct()
            .forEach(r -> 
                System.out.println("  ID: " + r.getId() + ", Название: " + r.getName() + 
                    ", Кухня: " + r.getCuisineType().getDisplayName() + 
                    ", Средний чек: " + r.getAverageCheck() + 
                    ", Рейтинг: " + r.getRating()));
        
        System.out.println("\nОценки:");
        ratingService.findAll().forEach(rt -> 
            System.out.println("  ID: " + rt.getId() + ", Посетитель: " + rt.getVisitorId() + 
                ", Ресторан: " + rt.getRestaurantId() + 
                ", Оценка: " + rt.getScore() + 
                ", Отзыв: " + (rt.getReview() != null ? rt.getReview() : "Без отзыва")));
        
        System.out.println("\n=== Инициализация завершена ===");
    }
} 