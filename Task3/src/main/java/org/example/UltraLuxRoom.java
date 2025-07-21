package org.example;

public class UltraLuxRoom extends LuxRoom {
    
    public UltraLuxRoom(int roomNumber, int maxOccupancy, int pricePerNight) {
        super(roomNumber, maxOccupancy, pricePerNight);
    }
    
    public UltraLuxRoom(int roomNumber, int pricePerNight) {
        super(roomNumber, pricePerNight);
    }
} 