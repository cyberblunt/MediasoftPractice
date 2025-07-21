package org.example;

public interface RoomService<T extends Room> {
    
    void clean(T room);
    
    void reserve(T room) throws RoomAlreadyReservedException;
    
    void free(T room);
} 