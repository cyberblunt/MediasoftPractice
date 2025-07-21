# Задание к лекции 8 - Модульные и интеграционные тесты

## Описание проекта

Данный проект представляет собой REST API для системы управления ресторанами и отзывами. Приложение построено на Spring Boot с использованием JPA для работы с базой данных H2.

## Архитектура проекта

### Сущности (Entities)
- **User** - пользователи системы
- **Restaurant** - рестораны
- **Review** - отзывы пользователей о ресторанах

### Слои приложения
- **Repository** - слой доступа к данным
- **Service** - бизнес-логика
- **Controller** - REST API контроллеры

## Структура проекта

```
src/
├── main/
│   ├── java/org/example/
│   │   ├── entity/
│   │   │   ├── User.java
│   │   │   ├── Restaurant.java
│   │   │   └── Review.java
│   │   ├── repository/
│   │   │   ├── UserRepository.java
│   │   │   ├── RestaurantRepository.java
│   │   │   └── ReviewRepository.java
│   │   ├── service/
│   │   │   ├── UserService.java
│   │   │   ├── RestaurantService.java
│   │   │   └── ReviewService.java
│   │   ├── controller/
│   │   │   ├── UserController.java
│   │   │   ├── RestaurantController.java
│   │   │   └── ReviewController.java
│   │   └── Main.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/org/example/
        ├── service/
        │   ├── UserServiceTest.java
        │   ├── RestaurantServiceTest.java
        │   └── ReviewServiceTest.java
        └── controller/
            ├── UserControllerTest.java
            ├── RestaurantControllerTest.java
            └── ReviewControllerTest.java
```

## Тестирование

### Модульные тесты (Unit Tests)

Модульные тесты написаны для сервисного слоя с использованием:
- **JUnit 5** - фреймворк для тестирования
- **Mockito** - библиотека для мокирования зависимостей

#### Тестируемые сервисы:
1. **UserServiceTest** - тесты для UserService
2. **RestaurantServiceTest** - тесты для RestaurantService  
3. **ReviewServiceTest** - тесты для ReviewService

#### Особенности модульных тестов:
- Использование аннотаций `@Mock` и `@InjectMocks`
- Мокирование репозиториев для изоляции тестируемого кода
- Проверка всех CRUD операций
- Тестирование граничных случаев и исключений

### Интеграционные тесты (Integration Tests)

Интеграционные тесты написаны для контроллерного слоя с использованием:
- **Spring Boot Test** - для тестирования Spring приложений
- **@WebMvcTest** - для тестирования только веб-слоя
- **MockMvc** - для имитации HTTP-запросов
- **@MockBean** - для мокирования сервисов

#### Тестируемые контроллеры:
1. **UserControllerTest** - тесты для UserController
2. **RestaurantControllerTest** - тесты для RestaurantController
3. **ReviewControllerTest** - тесты для ReviewController

#### Проверяемые HTTP операции:
- **GET** - получение данных
- **POST** - создание новых ресурсов
- **PUT** - обновление существующих ресурсов
- **DELETE** - удаление ресурсов

## Запуск тестов

### Запуск всех тестов
```bash
./gradlew test
```

### Запуск только модульных тестов
```bash
./gradlew test --tests "*ServiceTest"
```

### Запуск только интеграционных тестов
```bash
./gradlew test --tests "*ControllerTest"
```

### Запуск конкретного теста
```bash
./gradlew test --tests "UserServiceTest"
```

## Зависимости

### Основные зависимости:
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database
- Spring Boot Test

### Тестовые зависимости:
- JUnit 5
- Mockito
- Spring Boot Test Starter

## Конфигурация

Приложение использует H2 in-memory базу данных для тестирования. Конфигурация находится в `src/main/resources/application.properties`.

## API Endpoints

### Users
- `GET /api/users` - получить всех пользователей
- `GET /api/users/{id}` - получить пользователя по ID
- `POST /api/users` - создать нового пользователя
- `PUT /api/users/{id}` - обновить пользователя
- `DELETE /api/users/{id}` - удалить пользователя

### Restaurants
- `GET /api/restaurants` - получить все рестораны
- `GET /api/restaurants/{id}` - получить ресторан по ID
- `GET /api/restaurants/cuisine/{cuisine}` - получить рестораны по кухне
- `POST /api/restaurants` - создать новый ресторан
- `PUT /api/restaurants/{id}` - обновить ресторан
- `DELETE /api/restaurants/{id}` - удалить ресторан

### Reviews
- `GET /api/reviews` - получить все отзывы
- `GET /api/reviews/{id}` - получить отзыв по ID
- `GET /api/reviews/restaurant/{restaurantId}` - получить отзывы по ресторану
- `GET /api/reviews/user/{userId}` - получить отзывы по пользователю
- `POST /api/reviews` - создать новый отзыв
- `PUT /api/reviews/{id}` - обновить отзыв
- `DELETE /api/reviews/{id}` - удалить отзыв 