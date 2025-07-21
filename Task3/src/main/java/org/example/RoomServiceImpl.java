package org.example;

public class RoomServiceImpl<T extends Room> implements RoomService<T> {
    
    @Override
    public void clean(T room) {
        System.out.println("Комната " + room.getRoomNumber() + " была убрана");
    }
    
    @Override
    public void reserve(T room) throws RoomAlreadyReservedException {
        if (room.isReserved()) {
            throw new RoomAlreadyReservedException("Комната " + room.getRoomNumber() + " уже забронирована!");
        }
        room.setReserved(true);
        System.out.println("Комната " + room.getRoomNumber() + " была забронирована");
    }
    
    @Override
    public void free(T room) {
        room.setReserved(false);
        System.out.println("Комната " + room.getRoomNumber() + " была освобождена");
    }
} 