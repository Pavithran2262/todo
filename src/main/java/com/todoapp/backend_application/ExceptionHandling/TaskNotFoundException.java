package com.todoapp.backend_application.ExceptionHandling;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String message) {
        super(message);
    }
}


