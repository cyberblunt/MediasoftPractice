package org.example;

public class EconomyRoom extends Room {
    
    public EconomyRoom(int roomNumber, int maxOccupancy, int pricePerNight) {
        super(roomNumber, maxOccupancy, pricePerNight);
    }
    
    public EconomyRoom(int roomNumber, int pricePerNight) {
        super(roomNumber, pricePerNight);
    }
} 