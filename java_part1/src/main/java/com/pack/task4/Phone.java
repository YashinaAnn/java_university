package com.pack.task4;

import java.util.Objects;

public class Phone {

    private String phone;

    public Phone(String phone) {
        setPhone(phone);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        validatePhone(phone);
        this.phone = phone;
    }

    public static boolean isCorrectPhone(String phone) {
        return (null != phone && !phone.isEmpty())
                && (phone.length() == 9 && phone.matches("[0-9]+")
                || (phone.matches("\\d\\d-\\d\\d-\\d\\d")));
    }

    public static void validatePhone(String phone) {
        if (!isCorrectPhone(phone)) {
            throw new IllegalArgumentException("Incorrect phone format");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone1 = (Phone) o;
        return Objects.equals(phone, phone1.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone);
    }

    public String toString() {
        return "Phone: " + phone;
    }
}
