package com.pack.task4;

import java.util.Objects;

public class PersonalInfo {

    private String firstName;
    private String lastName;
    private Address address;

    public PersonalInfo() {}

    public PersonalInfo(String firstName, String lastName, Address address) {
        setFirstName(firstName);
        setLastName(lastName);
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (null == firstName || firstName.isEmpty()) {
            throw new IllegalArgumentException("Bad First Name: " + firstName);
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (null == lastName || lastName.isEmpty()) {
            throw new IllegalArgumentException("Bad Last Name: " + lastName);
        }
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalInfo that = (PersonalInfo) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, address);
    }

    public String toString() {
        return String.format("Name: %s %s\n", firstName, lastName) + address.toString();
    }
}
