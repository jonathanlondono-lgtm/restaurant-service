package com.plazoleta.plazoleta_service.domain.exception;

public class InvalidDishFieldException extends RuntimeException {
    public InvalidDishFieldException(String message) {
        super(message);
    }
}

