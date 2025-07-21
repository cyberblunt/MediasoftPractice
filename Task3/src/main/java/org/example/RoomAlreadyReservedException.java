package org.example;

public class RoomAlreadyReservedException extends RuntimeException {
    
    public RoomAlreadyReservedException(String message) {
        super(message);
    }
    
    public RoomAlreadyReservedException(String message, Throwable cause) {
        super(message, cause);
    }
} 