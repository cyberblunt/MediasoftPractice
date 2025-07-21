package org.example;

public class StandartRoom extends ProRoom {
    
    public StandartRoom(int roomNumber, int maxOccupancy, int pricePerNight) {
        super(roomNumber, maxOccupancy, pricePerNight);
    }
    
    public StandartRoom(int roomNumber, int pricePerNight) {
        super(roomNumber, pricePerNight);
    }
} 