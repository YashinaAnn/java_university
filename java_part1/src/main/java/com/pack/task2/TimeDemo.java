package com.pack.task2;

public class TimeDemo {

    public static void main(String[] args) {
        Time time = new Time(23, 5, 57);
        System.out.println("Time: " + time);
        System.out.println("Time to seconds: " + time.toSeconds());
        time.inFiveSeconds();
        System.out.println("Time in 5 seconds: " + time);
        time.setMinutes(59);
        time.setSeconds(59);
        System.out.println("Time: " + time);
        time.inFiveSeconds();
        System.out.println("Time in 5 seconds: " + time);
    }
}
