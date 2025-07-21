package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Review;
import org.example.entity.User;
import org.example.entity.Restaurant;
import org.example.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void getAllReviews_ShouldReturnReviewsList() throws Exception {
        // Arrange
        when(reviewService.getAllReviews()).thenReturn(Arrays.asList(testReview));

        // Act & Assert
        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].rating").value(5))
                .andExpect(jsonPath("$[0].comment").value("Great food!"));

        verify(reviewService, times(1)).getAllReviews();
    }

    @Test
    void getReviewById_WhenReviewExists_ShouldReturnReview() throws Exception {
        // Arrange
        when(reviewService.getReviewById(1L)).thenReturn(Optional.of(testReview));

        // Act & Assert
        mockMvc.perform(get("/api/reviews/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.comment").value("Great food!"));

        verify(reviewService, times(1)).getReviewById(1L);
    }

    @Test
    void getReviewById_WhenReviewDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(reviewService.getReviewById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/reviews/999"))
                .andExpect(status().isNotFound());

        verify(reviewService, times(1)).getReviewById(999L);
    }

    @Test
    void getReviewsByRestaurant_ShouldReturnFilteredReviews() throws Exception {
        // Arrange
        when(reviewService.getReviewsByRestaurant(1L)).thenReturn(Arrays.asList(testReview));

        // Act & Assert
        mockMvc.perform(get("/api/reviews/restaurant/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].rating").value(5))
                .andExpect(jsonPath("$[0].comment").value("Great food!"));

        verify(reviewService, times(1)).getReviewsByRestaurant(1L);
    }

    @Test
    void getReviewsByUser_ShouldReturnFilteredReviews() throws Exception {
        // Arrange
        when(reviewService.getReviewsByUser(1L)).thenReturn(Arrays.asList(testReview));

        // Act & Assert
        mockMvc.perform(get("/api/reviews/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].rating").value(5))
                .andExpect(jsonPath("$[0].comment").value("Great food!"));

        verify(reviewService, times(1)).getReviewsByUser(1L);
    }

    @Test
    void createReview_WhenValidReview_ShouldReturnCreatedReview() throws Exception {
        // Arrange
        Review newReview = new Review();
        newReview.setUser(testUser);
        newReview.setRestaurant(testRestaurant);
        newReview.setRating(4);
        newReview.setComment("Good food");

        when(reviewService.createReview(any(Review.class))).thenReturn(testReview);

        // Act & Assert
        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newReview)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.comment").value("Great food!"));

        verify(reviewService, times(1)).createReview(any(Review.class));
    }

    @Test
    void createReview_WhenInvalidReview_ShouldReturnBadRequest() throws Exception {
        // Arrange
        Review invalidReview = new Review();
        invalidReview.setUser(testUser);
        invalidReview.setRestaurant(testRestaurant);

        when(reviewService.createReview(any(Review.class)))
                .thenThrow(new RuntimeException("User not found"));

        // Act & Assert
        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidReview)))
                .andExpect(status().isBadRequest());

        verify(reviewService, times(1)).createReview(any(Review.class));
    }

    @Test
    void updateReview_WhenReviewExists_ShouldReturnUpdatedReview() throws Exception {
        // Arrange
        Review updateDetails = new Review();
        updateDetails.setRating(3);
        updateDetails.setComment("Updated comment");

        when(reviewService.updateReview(eq(1L), any(Review.class))).thenReturn(testReview);

        // Act & Assert
        mockMvc.perform(put("/api/reviews/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.comment").value("Great food!"));

        verify(reviewService, times(1)).updateReview(eq(1L), any(Review.class));
    }

    @Test
    void updateReview_WhenReviewDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        Review updateDetails = new Review();
        updateDetails.setRating(3);
        updateDetails.setComment("Updated comment");

        when(reviewService.updateReview(eq(999L), any(Review.class)))
                .thenThrow(new RuntimeException("Review not found"));

        // Act & Assert
        mockMvc.perform(put("/api/reviews/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDetails)))
                .andExpect(status().isNotFound());

        verify(reviewService, times(1)).updateReview(eq(999L), any(Review.class));
    }

    @Test
    void deleteReview_WhenReviewExists_ShouldReturnOk() throws Exception {
        // Arrange
        doNothing().when(reviewService).deleteReview(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/reviews/1"))
                .andExpect(status().isOk());

        verify(reviewService, times(1)).deleteReview(1L);
    }

    @Test
    void deleteReview_WhenReviewDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        doThrow(new RuntimeException("Review not found")).when(reviewService).deleteReview(999L);

        // Act & Assert
        mockMvc.perform(delete("/api/reviews/999"))
                .andExpect(status().isNotFound());

        verify(reviewService, times(1)).deleteReview(999L);
    }
} 