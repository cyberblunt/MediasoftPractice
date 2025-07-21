package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Restaurant;
import org.example.service.RestaurantService;
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

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void getAllRestaurants_ShouldReturnRestaurantsList() throws Exception {
        // Arrange
        when(restaurantService.getAllRestaurants()).thenReturn(Arrays.asList(testRestaurant));

        // Act & Assert
        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Restaurant"))
                .andExpect(jsonPath("$[0].address").value("Test Address"))
                .andExpect(jsonPath("$[0].cuisine").value("Italian"));

        verify(restaurantService, times(1)).getAllRestaurants();
    }

    @Test
    void getRestaurantById_WhenRestaurantExists_ShouldReturnRestaurant() throws Exception {
        // Arrange
        when(restaurantService.getRestaurantById(1L)).thenReturn(Optional.of(testRestaurant));

        // Act & Assert
        mockMvc.perform(get("/api/restaurants/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Restaurant"))
                .andExpect(jsonPath("$.address").value("Test Address"))
                .andExpect(jsonPath("$.cuisine").value("Italian"));

        verify(restaurantService, times(1)).getRestaurantById(1L);
    }

    @Test
    void getRestaurantById_WhenRestaurantDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(restaurantService.getRestaurantById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/restaurants/999"))
                .andExpect(status().isNotFound());

        verify(restaurantService, times(1)).getRestaurantById(999L);
    }

    @Test
    void getRestaurantsByCuisine_ShouldReturnFilteredRestaurants() throws Exception {
        // Arrange
        when(restaurantService.getRestaurantsByCuisine("Italian")).thenReturn(Arrays.asList(testRestaurant));

        // Act & Assert
        mockMvc.perform(get("/api/restaurants/cuisine/Italian"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Restaurant"))
                .andExpect(jsonPath("$[0].cuisine").value("Italian"));

        verify(restaurantService, times(1)).getRestaurantsByCuisine("Italian");
    }

    @Test
    void createRestaurant_ShouldReturnCreatedRestaurant() throws Exception {
        // Arrange
        Restaurant newRestaurant = new Restaurant("New Restaurant", "New Address", "French");
        when(restaurantService.createRestaurant(any(Restaurant.class))).thenReturn(testRestaurant);

        // Act & Assert
        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRestaurant)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Restaurant"))
                .andExpect(jsonPath("$.address").value("Test Address"))
                .andExpect(jsonPath("$.cuisine").value("Italian"));

        verify(restaurantService, times(1)).createRestaurant(any(Restaurant.class));
    }

    @Test
    void updateRestaurant_WhenRestaurantExists_ShouldReturnUpdatedRestaurant() throws Exception {
        // Arrange
        Restaurant updateDetails = new Restaurant("Updated Restaurant", "Updated Address", "Spanish");
        when(restaurantService.updateRestaurant(eq(1L), any(Restaurant.class))).thenReturn(testRestaurant);

        // Act & Assert
        mockMvc.perform(put("/api/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Restaurant"))
                .andExpect(jsonPath("$.address").value("Test Address"))
                .andExpect(jsonPath("$.cuisine").value("Italian"));

        verify(restaurantService, times(1)).updateRestaurant(eq(1L), any(Restaurant.class));
    }

    @Test
    void updateRestaurant_WhenRestaurantDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        Restaurant updateDetails = new Restaurant("Updated Restaurant", "Updated Address", "Spanish");
        when(restaurantService.updateRestaurant(eq(999L), any(Restaurant.class)))
                .thenThrow(new RuntimeException("Restaurant not found"));

        // Act & Assert
        mockMvc.perform(put("/api/restaurants/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDetails)))
                .andExpect(status().isNotFound());

        verify(restaurantService, times(1)).updateRestaurant(eq(999L), any(Restaurant.class));
    }

    @Test
    void deleteRestaurant_WhenRestaurantExists_ShouldReturnOk() throws Exception {
        // Arrange
        doNothing().when(restaurantService).deleteRestaurant(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/restaurants/1"))
                .andExpect(status().isOk());

        verify(restaurantService, times(1)).deleteRestaurant(1L);
    }

    @Test
    void deleteRestaurant_WhenRestaurantDoesNotExist_ShouldReturnNotFound() throws Exception {
        // Arrange
        doThrow(new RuntimeException("Restaurant not found")).when(restaurantService).deleteRestaurant(999L);

        // Act & Assert
        mockMvc.perform(delete("/api/restaurants/999"))
                .andExpect(status().isNotFound());

        verify(restaurantService, times(1)).deleteRestaurant(999L);
    }
} 