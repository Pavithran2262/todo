package com.todoapp.backend_application.ExceptionHandling;

import com.todoapp.backend_application.API_Response.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {
    @Autowired
    APIResponse apiResponse;

     Logger log = LoggerFactory.getLogger(GlobalException.class);
    @ExceptionHandler(TaskNotFoundException.class)
    public APIResponse handleTaskNotFoundException(TaskNotFoundException ex) {
        log.error("Task not found .........");
        apiResponse.setMessage("Failed : Task not found");
        apiResponse.setStatus(HttpStatus.NOT_FOUND);
        apiResponse.setData(null);
        return apiResponse;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public APIResponse handleUserNotFoundException(UserNotFoundException ex) {
        log.error("User not found .....");
        apiResponse.setMessage("Failed : Incorrect User name or Password.........");
        apiResponse.setStatus(HttpStatus.NOT_FOUND);
        apiResponse.setData(null);
        return apiResponse;
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public APIResponse handleUserAlreadyExistException(UserAlreadyExistException ex) {
        log.error("Username Already Exist .....");
        apiResponse.setMessage("Failed : Username Already Exist, Use difference username........");
        apiResponse.setStatus(HttpStatus.BAD_REQUEST);
        apiResponse.setData(null);
        return apiResponse;
    }
}
