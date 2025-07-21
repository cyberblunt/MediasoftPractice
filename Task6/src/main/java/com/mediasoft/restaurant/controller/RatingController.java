package com.mediasoft.restaurant.controller;

import com.mediasoft.restaurant.dto.RatingRequestDto;
import com.mediasoft.restaurant.dto.RatingResponseDto;
import com.mediasoft.restaurant.service.RatingService;
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
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Отзывы", description = "API для работы с отзывами о ресторанах")
public class RatingController {

    private final RatingService ratingService;

    @GetMapping
    @Operation(summary = "Получить все отзывы", description = "Возвращает список всех отзывов")
    @ApiResponse(responseCode = "200", description = "Список отзывов получен успешно")
    public ResponseEntity<List<RatingResponseDto>> getAllRatings() {
        List<RatingResponseDto> ratings = ratingService.findAll();
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить отзыв по ID", description = "Возвращает отзыв по указанному ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Отзыв найден"),
        @ApiResponse(responseCode = "404", description = "Отзыв не найден")
    })
    public ResponseEntity<RatingResponseDto> getRatingById(
            @Parameter(description = "ID отзыва") @PathVariable Long id) {
        return ratingService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать новый отзыв", description = "Создает новый отзыв о ресторане")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Отзыв создан успешно"),
        @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    public ResponseEntity<RatingResponseDto> createRating(
            @Parameter(description = "Данные отзыва") 
            @Valid @RequestBody RatingRequestDto requestDto) {
        RatingResponseDto createdRating = ratingService.save(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRating);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить отзыв", description = "Обновляет отзыв по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Отзыв обновлен успешно"),
        @ApiResponse(responseCode = "404", description = "Отзыв не найден"),
        @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    public ResponseEntity<RatingResponseDto> updateRating(
            @Parameter(description = "ID отзыва") @PathVariable Long id,
            @Parameter(description = "Новые данные отзыва") 
            @Valid @RequestBody RatingRequestDto requestDto) {
        return ratingService.update(id, requestDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить отзыв", description = "Удаляет отзыв по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Отзыв удален успешно"),
        @ApiResponse(responseCode = "404", description = "Отзыв не найден")
    })
    public ResponseEntity<Void> deleteRating(
            @Parameter(description = "ID отзыва") @PathVariable Long id) {
        if (ratingService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
} 