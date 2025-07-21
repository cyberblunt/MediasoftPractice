package com.mediasoft.restaurant.service;

import com.mediasoft.restaurant.entity.Rating;
import com.mediasoft.restaurant.entity.Restaurant;
import com.mediasoft.restaurant.entity.Visitor;
import com.mediasoft.restaurant.repository.RatingRepository;
import com.mediasoft.restaurant.repository.RestaurantRepository;
import com.mediasoft.restaurant.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RatingService {
    private final RatingRepository ratingRepository;
    private final RestaurantRepository restaurantRepository;
    private final VisitorRepository visitorRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository, 
                        RestaurantRepository restaurantRepository,
                        VisitorRepository visitorRepository) {
        this.ratingRepository = ratingRepository;
        this.restaurantRepository = restaurantRepository;
        this.visitorRepository = visitorRepository;
    }

    public Rating save(Rating rating) {
        // Устанавливаем связи с сущностями
        if (rating.getVisitorId() != null) {
            Optional<Visitor> visitor = visitorRepository.findById(rating.getVisitorId());
            visitor.ifPresent(rating::setVisitor);
        }
        
        if (rating.getRestaurantId() != null) {
            Optional<Restaurant> restaurant = restaurantRepository.findById(rating.getRestaurantId());
            restaurant.ifPresent(rating::setRestaurant);
        }
        
        Rating savedRating = ratingRepository.save(rating);
        recalculateRestaurantRating(rating.getRestaurantId());
        return savedRating;
    }

    public void remove(Long id) {
        Optional<Rating> ratingOpt = ratingRepository.findById(id);
        if (ratingOpt.isPresent()) {
            Rating rating = ratingOpt.get();
            Long restaurantId = rating.getRestaurantId();
            ratingRepository.deleteById(id);
            recalculateRestaurantRating(restaurantId);
        }
    }

    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }
    
    public Optional<Rating> findById(Long id) {
        return ratingRepository.findById(id);
    }

    private void recalculateRestaurantRating(Long restaurantId) {
        List<Rating> restaurantRatings = ratingRepository.findByRestaurantId(restaurantId);

        if (!restaurantRatings.isEmpty()) {
            double averageScore = restaurantRatings.stream()
                    .mapToInt(Rating::getScore)
                    .average()
                    .orElse(0.0);

            BigDecimal newRating = BigDecimal.valueOf(averageScore)
                    .setScale(2, RoundingMode.HALF_UP);

            // Обновляем рейтинг ресторана
            Optional<Restaurant> restaurantOpt = restaurantRepository.findById(restaurantId);
            if (restaurantOpt.isPresent()) {
                Restaurant restaurant = restaurantOpt.get();
                restaurant.setRating(newRating);
                restaurantRepository.save(restaurant);
            }
        }
    }
} 