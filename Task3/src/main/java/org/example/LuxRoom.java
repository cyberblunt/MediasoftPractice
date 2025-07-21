package org.example;

public class LuxRoom extends ProRoom {
    
    public LuxRoom(int roomNumber, int maxOccupancy, int pricePerNight) {
        super(roomNumber, maxOccupancy, pricePerNight);
    }
    
    public LuxRoom(int roomNumber, int pricePerNight) {
        super(roomNumber, pricePerNight);
    }
} 