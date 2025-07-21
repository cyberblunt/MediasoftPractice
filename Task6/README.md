# Система оценки ресторанов

RESTful API для системы оценки ресторанов, реализованное на Spring Boot с использованием DTO (Data Transfer Objects) и Swagger/OpenAPI документацией.

## Описание проекта

Система позволяет управлять:
- Посетителями ресторанов
- Ресторанами с различными типами кухни
- Отзывами и оценками ресторанов

При добавлении отзыва автоматически пересчитывается средний рейтинг ресторана.

## Технологии

- Java 17
- Spring Boot 3.2.0
- Spring Web
- Spring Validation
- Lombok
- Swagger/OpenAPI 3
- Gradle

## Структура проекта

```
src/main/java/com/mediasoft/restaurant/
├── controller/          # REST контроллеры
│   ├── VisitorController.java
│   ├── RestaurantController.java
│   └── RatingController.java
├── dto/                 # Data Transfer Objects
│   ├── VisitorRequestDto.java
│   ├── VisitorResponseDto.java
│   ├── RestaurantRequestDto.java
│   ├── RestaurantResponseDto.java
│   ├── RatingRequestDto.java
│   └── RatingResponseDto.java
├── entity/              # Сущности
│   ├── Visitor.java
│   ├── Restaurant.java
│   ├── Rating.java
│   └── CuisineType.java
├── repository/          # Репозитории
│   ├── VisitorRepository.java
│   ├── RestaurantRepository.java
│   └── RatingRepository.java
├── service/             # Сервисы
│   ├── VisitorService.java
│   ├── RestaurantService.java
│   └── RatingService.java
└── config/              # Конфигурация
    └── OpenApiConfig.java
```

## API Endpoints

### Посетители (/api/users)

- `GET /api/users` - Получить всех посетителей
- `GET /api/users/{id}` - Получить посетителя по ID
- `POST /api/users` - Создать нового посетителя
- `PUT /api/users/{id}` - Обновить посетителя
- `DELETE /api/users/{id}` - Удалить посетителя

### Рестораны (/api/restaurants)

- `GET /api/restaurants` - Получить все рестораны
- `GET /api/restaurants/{id}` - Получить ресторан по ID
- `POST /api/restaurants` - Создать новый ресторан
- `PUT /api/restaurants/{id}` - Обновить ресторан
- `DELETE /api/restaurants/{id}` - Удалить ресторан

### Отзывы (/api/reviews)

- `GET /api/reviews` - Получить все отзывы
- `GET /api/reviews/{id}` - Получить отзыв по ID
- `POST /api/reviews` - Создать новый отзыв
- `PUT /api/reviews/{id}` - Обновить отзыв
- `DELETE /api/reviews/{id}` - Удалить отзыв

## Типы кухни

- EUROPEAN - Европейская
- ITALIAN - Итальянская
- CHINESE - Китайская
- JAPANESE - Японская
- INDIAN - Индийская
- MEXICAN - Мексиканская
- RUSSIAN - Русская
- FRENCH - Французская
- THAI - Тайская
- MEDITERRANEAN - Средиземноморская

## Запуск проекта

1. Убедитесь, что у вас установлена Java 17
2. Клонируйте репозиторий
3. Выполните команду для сборки:
   ```bash
   ./gradlew build
   ```
4. Запустите приложение:
   ```bash
   ./gradlew bootRun
   ```

## Swagger UI

После запуска приложения документация API доступна по адресу:
http://localhost:8080/swagger-ui.html

## Примеры запросов

### Создание посетителя
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Иван",
    "age": 25,
    "gender": "Мужской"
  }'
```

### Создание ресторана
```bash
curl -X POST http://localhost:8080/api/restaurants \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Итальянский дворик",
    "description": "Аутентичная итальянская кухня",
    "cuisineType": "ITALIAN",
    "averageCheck": 1500.00
  }'
```

### Создание отзыва
```bash
curl -X POST http://localhost:8080/api/reviews \
  -H "Content-Type: application/json" \
  -d '{
    "visitorId": 1,
    "restaurantId": 1,
    "score": 5,
    "review": "Отличная паста и атмосфера!"
  }'
```

## Особенности реализации

1. **DTO Pattern**: Все данные передаются через DTO, а не через сущности напрямую
2. **Неизменяемые DTO**: Используются Lombok аннотации для создания неизменяемых объектов
3. **Автоматический пересчет рейтинга**: При добавлении/удалении/обновлении отзыва автоматически пересчитывается средний рейтинг ресторана
4. **Валидация**: Поддержка валидации входных данных
5. **Swagger документация**: Полная документация API с примерами запросов и ответов
6. **In-memory хранение**: Данные хранятся в памяти с использованием List

## Тестирование

При запуске приложения автоматически создаются тестовые данные:
- 3 посетителя (включая анонимного)
- 3 ресторана с разными типами кухни
- 6 отзывов с различными оценками

Результаты тестирования выводятся в консоль при запуске приложения. 