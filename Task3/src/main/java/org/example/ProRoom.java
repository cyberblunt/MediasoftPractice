package org.example;

public abstract class ProRoom extends Room {
    
    public ProRoom(int roomNumber, int maxOccupancy, int pricePerNight) {
        super(roomNumber, maxOccupancy, pricePerNight);
    }
    
    public ProRoom(int roomNumber, int pricePerNight) {
        super(roomNumber, pricePerNight);
    }
} 