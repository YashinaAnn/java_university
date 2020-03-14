package com.pack.model;

import com.pack.dto.UserDto;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private long id;
    private String login;
    private String password;
    private String address;
    private String phone;

    public User(long id, String login, String password, String address, String phone) {
        setId(id);
        setLogin(login);
        setPassword(password);
        setPhone(phone);
        setAddress(address);
    }

    public User(String login, String password, String address,  String phone) {
        this(0, login, password, address, phone);
    }

    public User(UserDto userDto) {
        this(userDto.getLogin(), userDto.getPassword(), userDto.getAddress(), userDto.getPhone());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
       validateLogin(login);
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (null == password || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        validatePhone(phone);
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (null == address || address.isEmpty()) {
            throw new IllegalArgumentException("Bad address value");
        }
        this.address = address;
    }

    private static boolean isCorrectPhone(String phone) {
        return (null != phone && !phone.isEmpty())
                && (phone.length() == 10 && phone.matches("[0-9]+"));
    }

    public static void validatePhone(String phone) {
        if (!isCorrectPhone(phone)) {
            throw new IllegalArgumentException("Bad phone format: " + phone
                    + "\n Please enter phone in format: XXXXXXXXX");
        }
    }

    public static void validateLogin(String login) {
        if (null == login || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login is required");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(address, user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, phone, address);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
