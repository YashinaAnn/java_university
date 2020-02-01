package com.pack.task3;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoomTest {

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeLength() {
        Room room = new Room();
        room.setLength(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeWidth() {
        Room room = new Room();
        room.setWidth(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeHeight() {
        Room room = new Room();
        room.setHeight(-1);
    }

    @Test
    public void testWallsSquare() {
        Room room = new Room(20, 20, 10);
        System.out.println(room);
        double square = room.getWholeWallsSquare();
        double squareWallsOnly = room.getWallsSquare();
        assertEquals(800, square, 1);
        assertEquals(708, squareWallsOnly, 1);
    }

}
