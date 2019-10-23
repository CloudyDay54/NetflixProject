package com.netflix.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NotAllowedException extends RuntimeException{
    public NotAllowedException(String message) {
        super(message);
    }
}

