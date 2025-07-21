package org.example;

public class AbstractClassTest {
    public static void main(String[] args) {
        
        // Эти строки вызовут ошибку компиляции, так как Room и ProRoom являются абстрактными
        
        // Room room = new Room(101, 2, 1000);  // Ошибка компиляции!
        // ProRoom proRoom = new ProRoom(201, 3, 2000);  // Ошибка компиляции!
        
        // А эти строки работают нормально, так как классы не являются абстрактными
        EconomyRoom economyRoom = new EconomyRoom(101, 2, 1000);
        StandartRoom standartRoom = new StandartRoom(201, 3, 2000);
        LuxRoom luxRoom = new LuxRoom(301, 4, 3000);
        UltraLuxRoom ultraLuxRoom = new UltraLuxRoom(401, 2, 5000);
        
        System.out.println("Тест успешно прошел!");
        System.out.println("Экземпляры конкретных классов созданы:");
        System.out.println(economyRoom);
        System.out.println(standartRoom);
        System.out.println(luxRoom);
        System.out.println(ultraLuxRoom);
    }
} 