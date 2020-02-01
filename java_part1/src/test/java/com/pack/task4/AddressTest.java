package com.pack.task4;

import org.junit.Test;

public class AddressTest {

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullCity() {
        Address address = new Address();
        address.setCity(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetEmptyCity() {
        Address address = new Address();
        address.setCity("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetNullHouse() {
        Address address = new Address();
        address.setHouse(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetEmptyHouse() {
        Address address = new Address();
        address.setHouse("");
    }
}
