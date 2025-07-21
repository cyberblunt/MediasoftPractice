package com.mediasoft.restaurant.controller;

import com.mediasoft.restaurant.dto.RestaurantRequestDto;
import com.mediasoft.restaurant.dto.RestaurantResponseDto;
import com.mediasoft.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@Tag(name = "Рестораны", description = "API для работы с ресторанами")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    @Operation(summary = "Получить все рестораны", description = "Возвращает список всех ресторанов")
    @ApiResponse(responseCode = "200", description = "Список ресторанов получен успешно")
    public ResponseEntity<List<RestaurantResponseDto>> getAllRestaurants() {
        List<RestaurantResponseDto> restaurants = restaurantService.findAll();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить ресторан по ID", description = "Возвращает ресторан по указанному ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ресторан найден"),
        @ApiResponse(responseCode = "404", description = "Ресторан не найден")
    })
    public ResponseEntity<RestaurantResponseDto> getRestaurantById(
            @Parameter(description = "ID ресторана") @PathVariable Long id) {
        return restaurantService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать новый ресторан", description = "Создает новый ресторан")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ресторан создан успешно"),
        @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    public ResponseEntity<RestaurantResponseDto> createRestaurant(
            @Parameter(description = "Данные ресторана") 
            @Valid @RequestBody RestaurantRequestDto requestDto) {
        RestaurantResponseDto createdRestaurant = restaurantService.save(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurant);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить ресторан", description = "Обновляет данные ресторана по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ресторан обновлен успешно"),
        @ApiResponse(responseCode = "404", description = "Ресторан не найден"),
        @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    public ResponseEntity<RestaurantResponseDto> updateRestaurant(
            @Parameter(description = "ID ресторана") @PathVariable Long id,
            @Parameter(description = "Новые данные ресторана") 
            @Valid @RequestBody RestaurantRequestDto requestDto) {
        return restaurantService.update(id, requestDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить ресторан", description = "Удаляет ресторан по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ресторан удален успешно"),
        @ApiResponse(responseCode = "404", description = "Ресторан не найден")
    })
    public ResponseEntity<Void> deleteRestaurant(
            @Parameter(description = "ID ресторана") @PathVariable Long id) {
        if (restaurantService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
} 