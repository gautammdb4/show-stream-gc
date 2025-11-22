package com.showstream.userservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends  RuntimeException{

    public UserAlreadyExistsException(String identifier) {
        super("A user with identifier '" + identifier + "' already exists.");
    }
}
