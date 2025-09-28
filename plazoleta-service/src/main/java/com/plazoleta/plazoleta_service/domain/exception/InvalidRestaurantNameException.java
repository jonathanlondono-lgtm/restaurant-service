package com.plazoleta.plazoleta_service.domain.exception;

public class InvalidRestaurantNameException extends RuntimeException {
    public InvalidRestaurantNameException(String message) {
        super(message);
    }
}

