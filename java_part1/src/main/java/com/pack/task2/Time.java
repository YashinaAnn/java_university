package com.pack.task2;

import static java.lang.String.format;

public class Time {

    private static final int HOURS = 24;
    private static final int MINUTES = 60;
    private static final int SECONDS = 60;

    private int hours;
    private int minutes;
    private int seconds;

    public Time() {}

    public Time(int hours, int minutes, int seconds) {
        setHours(hours);
        setMinutes(minutes);
        setSeconds(seconds);
    }

    public Time(int hours) {
        setHours(hours);
    }

    public Time(int hours, int minutes) {
        this(hours);
        setMinutes(minutes);
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        if (hours < 0 || hours >= HOURS) {
            throw new IllegalArgumentException("Bad value of hours: " + hours);
        }
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        if (minutes < 0 || minutes >= MINUTES) {
            throw new IllegalArgumentException("Bad value of minutes: " + minutes);
        }
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        if (seconds < 0 || seconds >= SECONDS) {
            throw new IllegalArgumentException("Bad value of seconds: " + seconds);
        }
        this.seconds = seconds;
    }

    public int toSeconds() {
        return this.seconds + this.minutes * SECONDS + this.hours * MINUTES * SECONDS;
    }

    public void inNSeconds(int n) {
        seconds += n;
        if (seconds >= SECONDS) {
            seconds -= SECONDS;
            minutes++;
        }
        if (minutes >= MINUTES) {
            minutes -= MINUTES;
            hours++;
        }
        if (hours >= HOURS) {
            hours-= HOURS;
        }
    }

    public void inFiveSeconds() {
        inNSeconds(5);
    }

    public String toString() {
        return format("%s:%s:%s", toFormat(hours), toFormat(minutes), toFormat(seconds));
    }

    private String toFormat(int x) {
        return x < 10 ? "0" + x : String.valueOf(x);
    }
}
