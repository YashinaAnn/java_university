package com.pack.task2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeTest {

    @Test
    public void testInFiveSeconds_SecondsOnly() {
        Time time = new Time(1, 1, 1);
        System.out.println(time);
        time.inFiveSeconds();
        System.out.println(time);
        assertEquals(1, time.getHours());
        assertEquals(1, time.getMinutes());
        assertEquals(6, time.getSeconds());
    }

    @Test
    public void testInFiveSeconds_NextMinute() {
        Time time = new Time(1, 1, 58);
        System.out.println(time);
        time.inFiveSeconds();
        System.out.println(time);
        assertEquals(1, time.getHours());
        assertEquals(2, time.getMinutes());
        assertEquals(3, time.getSeconds());
    }

    @Test
    public void testInFiveSeconds_NextHour() {
        Time time = new Time(1, 59, 58);
        System.out.println(time);
        time.inFiveSeconds();
        System.out.println(time);
        assertEquals(2, time.getHours());
        assertEquals(0, time.getMinutes());
        assertEquals(3, time.getSeconds());
    }

    @Test
    public void testInFiveSeconds_NextDay() {
        Time time = new Time(23, 59, 58);
        System.out.println(time);
        time.inFiveSeconds();
        System.out.println(time);
        assertEquals(0, time.getHours());
        assertEquals(0, time.getMinutes());
        assertEquals(3, time.getSeconds());
    }

    @Test
    public void testToSeconds() {
        Time time = new Time(1, 59, 58);
        assertEquals(60 * 60 + 59 * 60 + 58, time.toSeconds());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeHours() {
        Time time = new Time();
        time.setHours(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetHoursGreater23() {
        Time time = new Time();
        time.setHours(24);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeMinutes() {
        Time time = new Time();
        time.setMinutes(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetMinutesGreater59() {
        Time time = new Time();
        time.setMinutes(60);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNegativeSeconds() {
        Time time = new Time();
        time.setSeconds(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetSecondsGreater59() {
        Time time = new Time();
        time.setSeconds(60);
    }
}
