package org.example.service;

import org.example.entity.Restaurant;
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
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    private Restaurant testRestaurant;

    @BeforeEach
    void setUp() {
        testRestaurant = new Restaurant();
        testRestaurant.setId(1L);
        testRestaurant.setName("Test Restaurant");
        testRestaurant.setAddress("Test Address");
        testRestaurant.setCuisine("Italian");
    }

    @Test
    void getAllRestaurants_ShouldReturnAllRestaurants() {
        // Arrange
        List<Restaurant> expectedRestaurants = Arrays.asList(testRestaurant);
        when(restaurantRepository.findAll()).thenReturn(expectedRestaurants);

        // Act
        List<Restaurant> actualRestaurants = restaurantService.getAllRestaurants();

        // Assert
        assertEquals(expectedRestaurants, actualRestaurants);
        verify(restaurantRepository, times(1)).findAll();
    }

    @Test
    void getRestaurantById_WhenRestaurantExists_ShouldReturnRestaurant() {
        // Arrange
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(testRestaurant));

        // Act
        Optional<Restaurant> actualRestaurant = restaurantService.getRestaurantById(1L);

        // Assert
        assertTrue(actualRestaurant.isPresent());
        assertEquals(testRestaurant, actualRestaurant.get());
        verify(restaurantRepository, times(1)).findById(1L);
    }

    @Test
    void getRestaurantById_WhenRestaurantDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(restaurantRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Restaurant> actualRestaurant = restaurantService.getRestaurantById(999L);

        // Assert
        assertFalse(actualRestaurant.isPresent());
        verify(restaurantRepository, times(1)).findById(999L);
    }

    @Test
    void createRestaurant_ShouldReturnSavedRestaurant() {
        // Arrange
        Restaurant restaurantToSave = new Restaurant("New Restaurant", "New Address", "French");
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(testRestaurant);

        // Act
        Restaurant savedRestaurant = restaurantService.createRestaurant(restaurantToSave);

        // Assert
        assertEquals(testRestaurant, savedRestaurant);
        verify(restaurantRepository, times(1)).save(restaurantToSave);
    }

    @Test
    void updateRestaurant_WhenRestaurantExists_ShouldReturnUpdatedRestaurant() {
        // Arrange
        Restaurant updateDetails = new Restaurant("Updated Restaurant", "Updated Address", "Spanish");
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(testRestaurant));
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(testRestaurant);

        // Act
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(1L, updateDetails);

        // Assert
        assertEquals(testRestaurant, updatedRestaurant);
        verify(restaurantRepository, times(1)).findById(1L);
        verify(restaurantRepository, times(1)).save(testRestaurant);
    }

    @Test
    void updateRestaurant_WhenRestaurantDoesNotExist_ShouldThrowException() {
        // Arrange
        Restaurant updateDetails = new Restaurant("Updated Restaurant", "Updated Address", "Spanish");
        when(restaurantRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> restaurantService.updateRestaurant(999L, updateDetails));
        verify(restaurantRepository, times(1)).findById(999L);
        verify(restaurantRepository, never()).save(any(Restaurant.class));
    }

    @Test
    void deleteRestaurant_ShouldCallRepositoryDelete() {
        // Act
        restaurantService.deleteRestaurant(1L);

        // Assert
        verify(restaurantRepository, times(1)).deleteById(1L);
    }

    @Test
    void getRestaurantsByCuisine_ShouldReturnFilteredRestaurants() {
        // Arrange
        List<Restaurant> expectedRestaurants = Arrays.asList(testRestaurant);
        when(restaurantRepository.findByCuisine("Italian")).thenReturn(expectedRestaurants);

        // Act
        List<Restaurant> actualRestaurants = restaurantService.getRestaurantsByCuisine("Italian");

        // Assert
        assertEquals(expectedRestaurants, actualRestaurants);
        verify(restaurantRepository, times(1)).findByCuisine("Italian");
    }
} 