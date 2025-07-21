package org.example;

import java.util.Random;

public abstract class Room {
    protected int roomNumber;
    protected int maxOccupancy;
    protected int pricePerNight;
    protected boolean isReserved;
    
    public Room(int roomNumber, int maxOccupancy, int pricePerNight) {
        this.roomNumber = roomNumber;
        this.maxOccupancy = maxOccupancy;
        this.pricePerNight = pricePerNight;
        this.isReserved = false;
    }
    
    // Конструктор со случайной генерацией количества человек
    public Room(int roomNumber, int pricePerNight) {
        this.roomNumber = roomNumber;
        this.maxOccupancy = new Random().nextInt(4) + 1; // от 1 до 4 человек
        this.pricePerNight = pricePerNight;
        this.isReserved = false;
    }
    
    // Геттеры и сеттеры
    public int getRoomNumber() {
        return roomNumber;
    }
    
    public int getMaxOccupancy() {
        return maxOccupancy;
    }
    
    public int getPricePerNight() {
        return pricePerNight;
    }
    
    public boolean isReserved() {
        return isReserved;
    }
    
    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + 
               " [Room " + roomNumber + 
               ", Max: " + maxOccupancy + 
               ", Price: " + pricePerNight + 
               ", Reserved: " + isReserved + "]";
    }
} 