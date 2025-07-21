package com.mediasoft.restaurant.service;

import com.mediasoft.restaurant.entity.Restaurant;
import com.mediasoft.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void remove(Long id) {
        restaurantRepository.deleteById(id);
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }
    
    public Optional<Restaurant> findById(Long id) {
        return restaurantRepository.findById(id);
    }
} 