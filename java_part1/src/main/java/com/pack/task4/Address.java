package com.pack.task4;

import java.util.Objects;

public class Address {

    private String country;
    private String city;
    private String street;
    private String house;

    public Address() {}

    public Address(String country, String city, String street, String house) {
        setCountry(country);
        setCity(city);
        setStreet(street);
        setHouse(house);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        if (null == country || country.isEmpty()) {
            throw new IllegalArgumentException("Incorrect country value!");
        }
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (null == city || city.isEmpty()) {
            throw new IllegalArgumentException("Incorrect city value!");
        }
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        if (null == street || street.isEmpty()) {
            throw new IllegalArgumentException("Incorrect street value!");
        }
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        if (null == house || house.isEmpty()) {
            throw new IllegalArgumentException("Incorrect house value!");
        }
        this.house = house;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return house == address.house &&
                Objects.equals(country, address.country) &&
                Objects.equals(city, address.city) &&
                Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, street, house);
    }

    public String toString() {
        return String.format("Address: %s, %s, %s, house - %s", country, city, street, house);
    }
}
