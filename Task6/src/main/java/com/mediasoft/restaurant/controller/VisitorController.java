package com.mediasoft.restaurant.controller;

import com.mediasoft.restaurant.dto.VisitorRequestDto;
import com.mediasoft.restaurant.dto.VisitorResponseDto;
import com.mediasoft.restaurant.service.VisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Посетители", description = "API для работы с посетителями ресторанов")
public class VisitorController {

    private final VisitorService visitorService;

    @GetMapping
    @Operation(summary = "Получить всех посетителей", description = "Возвращает список всех посетителей")
    @ApiResponse(responseCode = "200", description = "Список посетителей получен успешно")
    public ResponseEntity<List<VisitorResponseDto>> getAllVisitors() {
        List<VisitorResponseDto> visitors = visitorService.findAll();
        return ResponseEntity.ok(visitors);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить посетителя по ID", description = "Возвращает посетителя по указанному ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Посетитель найден"),
        @ApiResponse(responseCode = "404", description = "Посетитель не найден")
    })
    public ResponseEntity<VisitorResponseDto> getVisitorById(
            @Parameter(description = "ID посетителя") @PathVariable Long id) {
        return visitorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать нового посетителя", description = "Создает нового посетителя")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Посетитель создан успешно"),
        @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    public ResponseEntity<VisitorResponseDto> createVisitor(
            @Parameter(description = "Данные посетителя") 
            @Valid @RequestBody VisitorRequestDto requestDto) {
        VisitorResponseDto createdVisitor = visitorService.save(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVisitor);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить посетителя", description = "Обновляет данные посетителя по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Посетитель обновлен успешно"),
        @ApiResponse(responseCode = "404", description = "Посетитель не найден"),
        @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    public ResponseEntity<VisitorResponseDto> updateVisitor(
            @Parameter(description = "ID посетителя") @PathVariable Long id,
            @Parameter(description = "Новые данные посетителя") 
            @Valid @RequestBody VisitorRequestDto requestDto) {
        return visitorService.update(id, requestDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить посетителя", description = "Удаляет посетителя по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Посетитель удален успешно"),
        @ApiResponse(responseCode = "404", description = "Посетитель не найден")
    })
    public ResponseEntity<Void> deleteVisitor(
            @Parameter(description = "ID посетителя") @PathVariable Long id) {
        if (visitorService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
} 