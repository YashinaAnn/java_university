package com.pack.task4;

import org.junit.Test;

public class PersonalInfoTest {

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullFirstName() {
        PersonalInfo info = new PersonalInfo();
        info.setFirstName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetEmptyFirstName() {
        PersonalInfo info = new PersonalInfo();
        info.setFirstName("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullLastName() {
        PersonalInfo info = new PersonalInfo();
        info.setLastName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetEmptyLastName() {
        PersonalInfo info = new PersonalInfo();
        info.setLastName("");
    }
}
