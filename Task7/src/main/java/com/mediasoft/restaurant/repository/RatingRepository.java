package com.mediasoft.restaurant.repository;

import com.mediasoft.restaurant.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    
    @Query("SELECT r FROM Rating r WHERE r.restaurant.id = :restaurantId")
    List<Rating> findByRestaurantId(@Param("restaurantId") Long restaurantId);
    
    @Query("SELECT r FROM Rating r WHERE r.visitor.id = :visitorId")
    List<Rating> findByVisitorId(@Param("visitorId") Long visitorId);
} 