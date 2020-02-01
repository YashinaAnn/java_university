package com.pack.task4;

import org.junit.Test;

import static org.junit.Assert.*;

public class PhoneTest {

    @Test
    public void testValidPhone() {
        assertTrue(Phone.isCorrectPhone("12-12-12"));
        assertTrue(Phone.isCorrectPhone("123333312"));
    }

    @Test
    public void testLetterInPhone() {
        assertFalse(Phone.isCorrectPhone("12222222s"));
    }

    @Test
    public void testShortPhone() {
        assertFalse(Phone.isCorrectPhone("222"));
    }

    @Test
    public void testPhoneNull() {
        assertFalse(Phone.isCorrectPhone(null));
    }

    @Test
    public void testPhoneEmptyString() {
        assertFalse(Phone.isCorrectPhone(""));
    }
}
