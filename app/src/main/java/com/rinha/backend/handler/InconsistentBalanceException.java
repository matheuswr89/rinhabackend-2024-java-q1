package com.rinha.backend.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InconsistentBalanceException extends RuntimeException {
    public InconsistentBalanceException(String message) {
        super(message);
    }
}
