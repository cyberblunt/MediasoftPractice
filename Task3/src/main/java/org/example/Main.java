package org.example;

public class Main {
    public static void main(String[] args) {
        // Создаем экземпляры комнат различных типов
        EconomyRoom economyRoom = new EconomyRoom(101, 1000);
        StandartRoom standartRoom = new StandartRoom(201, 3, 2500);
        LuxRoom luxRoom = new LuxRoom(301, 4000);
        UltraLuxRoom ultraLuxRoom = new UltraLuxRoom(401, 2, 8000);
        
        // Создаем сервис для работы с комнатами
        RoomService<Room> roomService = new RoomServiceImpl<>();
        
        // Выводим информацию о комнатах
        System.out.println("=== Информация о комнатах ===");
        System.out.println(economyRoom);
        System.out.println(standartRoom);
        System.out.println(luxRoom);
        System.out.println(ultraLuxRoom);
        
        System.out.println("\n=== Тестирование сервиса ===");
        
        // Тестируем уборку комнат
        System.out.println("\n--- Уборка комнат ---");
        roomService.clean(economyRoom);
        roomService.clean(standartRoom);
        roomService.clean(luxRoom);
        roomService.clean(ultraLuxRoom);
        
        // Тестируем бронирование комнат
        System.out.println("\n--- Бронирование комнат ---");
        try {
            roomService.reserve(economyRoom);
            roomService.reserve(standartRoom);
            roomService.reserve(luxRoom);
            roomService.reserve(ultraLuxRoom);
        } catch (RoomAlreadyReservedException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        
        // Проверяем статус комнат после бронирования
        System.out.println("\n--- Статус комнат после бронирования ---");
        System.out.println(economyRoom);
        System.out.println(standartRoom);
        System.out.println(luxRoom);
        System.out.println(ultraLuxRoom);
        
        // Тестируем повторное бронирование (должно вызвать исключение)
        System.out.println("\n--- Попытка повторного бронирования ---");
        try {
            roomService.reserve(economyRoom);
        } catch (RoomAlreadyReservedException e) {
            System.out.println("Перехвачено исключение: " + e.getMessage());
        }
        
        // Освобождаем комнаты
        System.out.println("\n--- Освобождение комнат ---");
        roomService.free(economyRoom);
        roomService.free(standartRoom);
        roomService.free(luxRoom);
        roomService.free(ultraLuxRoom);
        
        // Проверяем статус комнат после освобождения
        System.out.println("\n--- Статус комнат после освобождения ---");
        System.out.println(economyRoom);
        System.out.println(standartRoom);
        System.out.println(luxRoom);
        System.out.println(ultraLuxRoom);
        
        // Демонстрируем работу с конкретными типами комнат
        System.out.println("\n=== Тестирование с конкретными типами ===");
        
        RoomService<EconomyRoom> economyRoomService = new RoomServiceImpl<>();
        RoomService<LuxRoom> luxRoomService = new RoomServiceImpl<>();
        
        economyRoomService.clean(economyRoom);
        luxRoomService.clean(luxRoom);
        
        try {
            economyRoomService.reserve(economyRoom);
            luxRoomService.reserve(luxRoom);
        } catch (RoomAlreadyReservedException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        
        System.out.println("\nТестирование завершено успешно!");
    }
}