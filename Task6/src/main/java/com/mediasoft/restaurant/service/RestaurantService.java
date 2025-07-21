package com.mediasoft.restaurant.service;

import com.mediasoft.restaurant.dto.RestaurantRequestDto;
import com.mediasoft.restaurant.dto.RestaurantResponseDto;
import com.mediasoft.restaurant.entity.Restaurant;
import com.mediasoft.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final AtomicLong idCounter = new AtomicLong(1);

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public RestaurantResponseDto save(RestaurantRequestDto requestDto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(idCounter.getAndIncrement());
        restaurant.setName(requestDto.getName());
        restaurant.setDescription(requestDto.getDescription());
        restaurant.setCuisineType(requestDto.getCuisineType());
        restaurant.setAverageCheck(requestDto.getAverageCheck());
        restaurant.setRating(BigDecimal.ZERO); // начальная оценка
        
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return convertToResponseDto(savedRestaurant);
    }

    public Optional<RestaurantResponseDto> update(Long id, RestaurantRequestDto requestDto) {
        Optional<Restaurant> existingRestaurant = restaurantRepository.findById(id);
        if (existingRestaurant.isPresent()) {
            Restaurant restaurant = existingRestaurant.get();
            restaurant.setName(requestDto.getName());
            restaurant.setDescription(requestDto.getDescription());
            restaurant.setCuisineType(requestDto.getCuisineType());
            restaurant.setAverageCheck(requestDto.getAverageCheck());
            // рейтинг не обновляем, он рассчитывается автоматически
            
            Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
            return Optional.of(convertToResponseDto(updatedRestaurant));
        }
        return Optional.empty();
    }

    public boolean delete(Long id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isPresent()) {
            restaurantRepository.remove(id);
            return true;
        }
        return false;
    }

    public List<RestaurantResponseDto> findAll() {
        return restaurantRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public Optional<RestaurantResponseDto> findById(Long id) {
        return restaurantRepository.findById(id)
                .map(this::convertToResponseDto);
    }

    private RestaurantResponseDto convertToResponseDto(Restaurant restaurant) {
        return new RestaurantResponseDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getDescription(),
                restaurant.getCuisineType(),
                restaurant.getAverageCheck(),
                restaurant.getRating()
        );
    }
} 