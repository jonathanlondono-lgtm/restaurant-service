package com.plazoleta.plazoleta_service.domain.exception;

public class InvalidDishPriceException extends RuntimeException {
    public InvalidDishPriceException(String message) {
        super(message);
    }
}

