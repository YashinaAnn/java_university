package com.pack.exceptions;

public enum ServiceError {

    LOGIN_EXISTS("Login already exists"),
    PHONE_EXISTS("Phone already exists"),

    USER_NOT_AUTHORIZED("User is not authorized"),
    UNKNOWN_USER("Username or/and password is not correct"),
    NOT_ENOUGH_MONEY("Not enough money on account"),
    ACCOUNT_NOT_FOUND("Account not found");

    ServiceError(String message){
        this.message = message;
    }

    private String message;

    public String message(){
        return message;
    }
}
