package com.plazoleta.plazoleta_service.domain.exception;

public class DishNotFoundInRestaurantException extends RuntimeException {
    public DishNotFoundInRestaurantException(String message) {
        super(message);
    }
}

