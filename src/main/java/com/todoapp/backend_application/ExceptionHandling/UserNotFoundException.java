package com.todoapp.backend_application.ExceptionHandling;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
        super(message);
    }
}
