package com.pack.database;

public class DatabaseException extends Exception {

    private String message;

    public DatabaseException(String message){
        this.message = message;
    }

    public DatabaseException(ServiceError error){
        this.message = error.getMessage();
    }

    public String getMessage(){
        return message;
    }
}
