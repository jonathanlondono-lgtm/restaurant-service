package com.plazoleta.plazoleta_service.domain.exception;

public class InvalidDishNameException extends RuntimeException {
    public InvalidDishNameException(String message) {
        super(message);
    }
}