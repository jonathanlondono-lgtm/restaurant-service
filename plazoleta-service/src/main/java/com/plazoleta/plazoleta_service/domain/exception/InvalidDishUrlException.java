package com.plazoleta.plazoleta_service.domain.exception;

public class InvalidDishUrlException extends RuntimeException {
    public InvalidDishUrlException(String message) {
        super(message);
    }
}

