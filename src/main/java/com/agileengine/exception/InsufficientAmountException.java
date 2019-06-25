package com.agileengine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InsufficientAmountException extends RuntimeException {

    public InsufficientAmountException(String s) {
        super(s);
    }
}
