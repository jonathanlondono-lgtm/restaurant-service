package com.plazoleta.plazoleta_service.domain.exception;

public class InvalidDishDescriptionException extends RuntimeException {
    public InvalidDishDescriptionException(String message) {
        super(message);
    }
}

