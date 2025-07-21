package com.mediasoft.restaurant;

import com.mediasoft.restaurant.entity.CuisineType;
import com.mediasoft.restaurant.entity.Rating;
import com.mediasoft.restaurant.entity.Restaurant;
import com.mediasoft.restaurant.entity.Visitor;
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
        Visitor visitor1 = new Visitor(null, "Анна", 25, "Женский");
        Visitor visitor2 = new Visitor(null, "Иван", 30, "Мужской");
        Visitor visitor3 = new Visitor(null, null, 28, "Женский"); // анонимный посетитель
        
        visitorService.save(visitor1);
        visitorService.save(visitor2);
        visitorService.save(visitor3);
        
        System.out.println("Создано посетителей: " + visitorService.findAll().size());
        
        // Создаем рестораны
        Restaurant restaurant1 = new Restaurant(null, "Итальянский дворик", 
                "Аутентичная итальянская кухня", CuisineType.ITALIAN, 
                new BigDecimal("1500.00"), new BigDecimal("0.00"));
        
        Restaurant restaurant2 = new Restaurant(null, "Суши-бар Сакура", 
                "Японская кухня и суши", CuisineType.JAPANESE, 
                new BigDecimal("2000.00"), new BigDecimal("0.00"));
        
        Restaurant restaurant3 = new Restaurant(null, "Европейский ресторан", 
                "", CuisineType.EUROPEAN, 
                new BigDecimal("1800.00"), new BigDecimal("0.00"));
        
        restaurantService.save(restaurant1);
        restaurantService.save(restaurant2);
        restaurantService.save(restaurant3);
        
        System.out.println("Создано ресторанов: " + restaurantService.findAll().size());
        
        // Создаем оценки
        Rating rating1 = new Rating(null, visitor1.getId(), restaurant1.getId(), 5, 
                "Отличная паста и атмосфера!");
        Rating rating2 = new Rating(null, visitor2.getId(), restaurant1.getId(), 4, 
                "Хорошая кухня, но дорого");
        Rating rating3 = new Rating(null, visitor3.getId(), restaurant1.getId(), 5, 
                "Вкусно!");
        
        Rating rating4 = new Rating(null, visitor1.getId(), restaurant2.getId(), 3, 
                "Неплохо, но могло быть лучше");
        Rating rating5 = new Rating(null, visitor2.getId(), restaurant2.getId(), 5, 
                "Лучшие суши в городе!");
        
        Rating rating6 = new Rating(null, visitor1.getId(), restaurant3.getId(), 4, 
                "Классическая европейская кухня");
        
        ratingService.save(rating1);
        ratingService.save(rating2);
        ratingService.save(rating3);
        ratingService.save(rating4);
        ratingService.save(rating5);
        ratingService.save(rating6);
        
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