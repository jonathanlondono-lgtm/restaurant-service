package com.plazoleta.plazoleta_service.domain.exception;

public class InvalidDishRestaurantIdException extends RuntimeException {
    public InvalidDishRestaurantIdException(String message) {
        super(message);
    }
}

