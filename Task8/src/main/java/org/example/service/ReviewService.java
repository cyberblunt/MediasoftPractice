package org.example.service;

import org.example.entity.Review;
import org.example.repository.ReviewRepository;
import org.example.repository.UserRepository;
import org.example.repository.RestaurantRepository;
import org.example.entity.User;
import org.example.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    
    @Autowired
    public ReviewService(ReviewRepository reviewRepository, 
                        UserRepository userRepository, 
                        RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }
    
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
    
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }
    
    public Review createReview(Review review) {
        // Проверяем, что пользователь и ресторан существуют
        User user = userRepository.findById(review.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Restaurant restaurant = restaurantRepository.findById(review.getRestaurant().getId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        
        review.setUser(user);
        review.setRestaurant(restaurant);
        
        return reviewRepository.save(review);
    }
    
    public Review updateReview(Long id, Review reviewDetails) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        
        review.setRating(reviewDetails.getRating());
        review.setComment(reviewDetails.getComment());
        
        return reviewRepository.save(review);
    }
    
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
    
    public List<Review> getReviewsByRestaurant(Long restaurantId) {
        return reviewRepository.findByRestaurantId(restaurantId);
    }
    
    public List<Review> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserId(userId);
    }
} 