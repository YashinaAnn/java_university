package com.pack.task3;

public class RoomDemo {

    public static void main(String[] args) {
        Room room = new Room(50, 40, 40);
        System.out.println("Room: \n" + room);
        System.out.println("Room square without window and door: " + room.getWholeWallsSquare());
        System.out.println("Room square: " + room.getWallsSquare());
    }

}
