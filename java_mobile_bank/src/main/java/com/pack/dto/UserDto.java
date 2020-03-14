package com.pack.dto;

public class UserDto {

    private String login;
    private String password;
    private String phone;
    private String address;

    public UserDto(String login, String password, String phone, String address) {
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
