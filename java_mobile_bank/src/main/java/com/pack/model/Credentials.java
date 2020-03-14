package com.pack.model;

import com.pack.dto.CredentialsDto;

import java.io.Serializable;

public class Credentials implements Serializable {

    private String login;
    private String password;

    public Credentials(String login, String password) {
        setLogin(login);
        setPassword(password);
    }

    public Credentials(CredentialsDto credentialsDto) {
        this(credentialsDto.getLogin(), credentialsDto.getPassword());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (null == login || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Value is required");
        }
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
}
