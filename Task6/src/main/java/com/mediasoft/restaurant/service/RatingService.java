package com.mediasoft.restaurant.service;

import com.mediasoft.restaurant.dto.RatingRequestDto;
import com.mediasoft.restaurant.dto.RatingResponseDto;
import com.mediasoft.restaurant.entity.Rating;
import com.mediasoft.restaurant.entity.Restaurant;
import com.mediasoft.restaurant.repository.RatingRepository;
import com.mediasoft.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final RestaurantRepository restaurantRepository;
    private final AtomicLong idCounter = new AtomicLong(1);

    @Autowired
    public RatingService(RatingRepository ratingRepository, RestaurantRepository restaurantRepository) {
        this.ratingRepository = ratingRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public RatingResponseDto save(RatingRequestDto requestDto) {
        Rating rating = new Rating();
        rating.setId(idCounter.getAndIncrement());
        rating.setVisitorId(requestDto.getVisitorId());
        rating.setRestaurantId(requestDto.getRestaurantId());
        rating.setScore(requestDto.getScore());
        rating.setReview(requestDto.getReview());
        
        Rating savedRating = ratingRepository.save(rating);
        recalculateRestaurantRating(rating.getRestaurantId());
        return convertToResponseDto(savedRating);
    }

    public Optional<RatingResponseDto> update(Long id, RatingRequestDto requestDto) {
        Optional<Rating> existingRating = ratingRepository.findById(id);
        if (existingRating.isPresent()) {
            Rating rating = existingRating.get();
            Long oldRestaurantId = rating.getRestaurantId();
            
            rating.setVisitorId(requestDto.getVisitorId());
            rating.setRestaurantId(requestDto.getRestaurantId());
            rating.setScore(requestDto.getScore());
            rating.setReview(requestDto.getReview());
            
            Rating updatedRating = ratingRepository.save(rating);
            
            // Пересчитываем рейтинги для старого и нового ресторана
            recalculateRestaurantRating(oldRestaurantId);
            if (!oldRestaurantId.equals(requestDto.getRestaurantId())) {
                recalculateRestaurantRating(requestDto.getRestaurantId());
            }
            
            return Optional.of(convertToResponseDto(updatedRating));
        }
        return Optional.empty();
    }

    public boolean delete(Long id) {
        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isPresent()) {
            Long restaurantId = rating.get().getRestaurantId();
            ratingRepository.remove(id);
            recalculateRestaurantRating(restaurantId);
            return true;
        }
        return false;
    }

    public List<RatingResponseDto> findAll() {
        return ratingRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<RatingResponseDto> findById(Long id) {
        return ratingRepository.findById(id)
                .map(this::convertToResponseDto);
    }

    private void recalculateRestaurantRating(Long restaurantId) {
        List<Rating> restaurantRatings = ratingRepository.findAll().stream()
                .filter(rating -> rating.getRestaurantId().equals(restaurantId))
                .toList();

        if (!restaurantRatings.isEmpty()) {
            double averageScore = restaurantRatings.stream()
                    .mapToInt(Rating::getScore)
                    .average()
                    .orElse(0.0);

            BigDecimal newRating = BigDecimal.valueOf(averageScore)
                    .setScale(2, RoundingMode.HALF_UP);

            // Обновляем рейтинг ресторана
            List<Restaurant> restaurants = restaurantRepository.findAll();
            restaurants.stream()
                    .filter(restaurant -> restaurant.getId().equals(restaurantId))
                    .findFirst()
                    .ifPresent(restaurant -> {
                        restaurant.setRating(newRating);
                        restaurantRepository.save(restaurant);
                    });
        }
    }

    private RatingResponseDto convertToResponseDto(Rating rating) {
        return new RatingResponseDto(
                rating.getId(),
                rating.getVisitorId(),
                rating.getRestaurantId(),
                rating.getScore(),
                rating.getReview()
        );
    }
} 