package org.example.service;

import org.example.entity.Review;
import org.example.entity.User;
import org.example.entity.Restaurant;
import org.example.repository.ReviewRepository;
import org.example.repository.UserRepository;
import org.example.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private ReviewService reviewService;

    private User testUser;
    private Restaurant testRestaurant;
    private Review testReview;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");

        testRestaurant = new Restaurant();
        testRestaurant.setId(1L);
        testRestaurant.setName("Test Restaurant");
        testRestaurant.setAddress("Test Address");
        testRestaurant.setCuisine("Italian");

        testReview = new Review();
        testReview.setId(1L);
        testReview.setUser(testUser);
        testReview.setRestaurant(testRestaurant);
        testReview.setRating(5);
        testReview.setComment("Great food!");
    }

    @Test
    void getAllReviews_ShouldReturnAllReviews() {
        // Arrange
        List<Review> expectedReviews = Arrays.asList(testReview);
        when(reviewRepository.findAll()).thenReturn(expectedReviews);

        // Act
        List<Review> actualReviews = reviewService.getAllReviews();

        // Assert
        assertEquals(expectedReviews, actualReviews);
        verify(reviewRepository, times(1)).findAll();
    }

    @Test
    void getReviewById_WhenReviewExists_ShouldReturnReview() {
        // Arrange
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(testReview));

        // Act
        Optional<Review> actualReview = reviewService.getReviewById(1L);

        // Assert
        assertTrue(actualReview.isPresent());
        assertEquals(testReview, actualReview.get());
        verify(reviewRepository, times(1)).findById(1L);
    }

    @Test
    void getReviewById_WhenReviewDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(reviewRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Review> actualReview = reviewService.getReviewById(999L);

        // Assert
        assertFalse(actualReview.isPresent());
        verify(reviewRepository, times(1)).findById(999L);
    }

    @Test
    void createReview_WhenUserAndRestaurantExist_ShouldReturnSavedReview() {
        // Arrange
        Review reviewToSave = new Review();
        reviewToSave.setUser(testUser);
        reviewToSave.setRestaurant(testRestaurant);
        reviewToSave.setRating(4);
        reviewToSave.setComment("Good food");

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(testRestaurant));
        when(reviewRepository.save(any(Review.class))).thenReturn(testReview);

        // Act
        Review savedReview = reviewService.createReview(reviewToSave);

        // Assert
        assertEquals(testReview, savedReview);
        verify(userRepository, times(1)).findById(1L);
        verify(restaurantRepository, times(1)).findById(1L);
        verify(reviewRepository, times(1)).save(reviewToSave);
    }

    @Test
    void createReview_WhenUserDoesNotExist_ShouldThrowException() {
        // Arrange
        Review reviewToSave = new Review();
        reviewToSave.setUser(testUser);
        reviewToSave.setRestaurant(testRestaurant);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> reviewService.createReview(reviewToSave));
        verify(userRepository, times(1)).findById(1L);
        verify(restaurantRepository, never()).findById(any());
        verify(reviewRepository, never()).save(any());
    }

    @Test
    void createReview_WhenRestaurantDoesNotExist_ShouldThrowException() {
        // Arrange
        Review reviewToSave = new Review();
        reviewToSave.setUser(testUser);
        reviewToSave.setRestaurant(testRestaurant);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> reviewService.createReview(reviewToSave));
        verify(userRepository, times(1)).findById(1L);
        verify(restaurantRepository, times(1)).findById(1L);
        verify(reviewRepository, never()).save(any());
    }

    @Test
    void updateReview_WhenReviewExists_ShouldReturnUpdatedReview() {
        // Arrange
        Review updateDetails = new Review();
        updateDetails.setRating(3);
        updateDetails.setComment("Updated comment");

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(testReview));
        when(reviewRepository.save(any(Review.class))).thenReturn(testReview);

        // Act
        Review updatedReview = reviewService.updateReview(1L, updateDetails);

        // Assert
        assertEquals(testReview, updatedReview);
        verify(reviewRepository, times(1)).findById(1L);
        verify(reviewRepository, times(1)).save(testReview);
    }

    @Test
    void updateReview_WhenReviewDoesNotExist_ShouldThrowException() {
        // Arrange
        Review updateDetails = new Review();
        updateDetails.setRating(3);
        updateDetails.setComment("Updated comment");

        when(reviewRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> reviewService.updateReview(999L, updateDetails));
        verify(reviewRepository, times(1)).findById(999L);
        verify(reviewRepository, never()).save(any());
    }

    @Test
    void deleteReview_ShouldCallRepositoryDelete() {
        // Act
        reviewService.deleteReview(1L);

        // Assert
        verify(reviewRepository, times(1)).deleteById(1L);
    }

    @Test
    void getReviewsByRestaurant_ShouldReturnFilteredReviews() {
        // Arrange
        List<Review> expectedReviews = Arrays.asList(testReview);
        when(reviewRepository.findByRestaurantId(1L)).thenReturn(expectedReviews);

        // Act
        List<Review> actualReviews = reviewService.getReviewsByRestaurant(1L);

        // Assert
        assertEquals(expectedReviews, actualReviews);
        verify(reviewRepository, times(1)).findByRestaurantId(1L);
    }

    @Test
    void getReviewsByUser_ShouldReturnFilteredReviews() {
        // Arrange
        List<Review> expectedReviews = Arrays.asList(testReview);
        when(reviewRepository.findByUserId(1L)).thenReturn(expectedReviews);

        // Act
        List<Review> actualReviews = reviewService.getReviewsByUser(1L);

        // Assert
        assertEquals(expectedReviews, actualReviews);
        verify(reviewRepository, times(1)).findByUserId(1L);
    }
} 