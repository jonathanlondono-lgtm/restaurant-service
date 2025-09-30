package com.plazoleta.plazoleta_service.domain.exception;

public class DishNotFoundForRestaurantException extends RuntimeException {
    public DishNotFoundForRestaurantException(String message) {
        super(message);
    }
}


