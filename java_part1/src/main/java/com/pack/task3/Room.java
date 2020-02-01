package com.pack.task3;

public class Room {

    private static final double WINDOW_LENGTH = 2;
    private static final double WINDOW_WIDTH = 15;
    private static final double DOOR_LENGTH = 2;
    private static final double DOOR_WIDTH = 8;

    private double length;
    private double width;
    private double height;

    public Room() {}

    public Room(double length, double width, double height) {
        setHeight(height);
        setLength(length);
        setWidth(width);
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        validateParameter("length", length);
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        validateParameter("width", width);
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        validateParameter("height", height);
        this.height = height;
    }

    private static void validateParameter(String name, double value) {
        if (value <= 0) {
            throw new IllegalArgumentException(String.format("Bad value of %s: %s", name, value));
        }
    }

    public double getWholeWallsSquare() {
        return 2 * (length * height + width * height);
    }

    public double getWallsSquare() {
        return getWallsSquare(WINDOW_LENGTH, WINDOW_WIDTH, DOOR_LENGTH, DOOR_WIDTH);
    }

    public double getWallsSquare(double windowX, double windowY, double doorX, double doorY) {
        return getWholeWallsSquare() - 2 * (windowX * windowY + doorX * doorY);
    }

    public String toString() {
        return String.format("Length: %s\nWidth: %s\nHeight: %s", length, width, height);
    }
}
