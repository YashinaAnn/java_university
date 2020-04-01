package com.university.accounts.web.exceptions;

public enum ServiceError {

    LOGIN_EXISTS("Login already exists"),
    PHONE_EXISTS("Phone already exists"),

    USER_DISABLED("User is disabled"),
    INVALID_CREDENTIALS("Invalid login/phone or password"),
    UNKNOWN_USER("User not authorized"),
    NOT_ENOUGH_MONEY("Not enough money on account"),
    ACCOUNT_NOT_FOUND("Account not found"),
    INCORRECT_PHONE_FORMAT("Incorrect phone format, should be in format: XXXXXXXXX");

    ServiceError(String message){
        this.message = message;
    }

    private String message;

    public String message(){
        return message;
    }
}
