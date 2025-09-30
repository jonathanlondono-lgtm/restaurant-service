package com.plazoleta.plazoleta_service.domain.exception;

public class InvalidDishCategoryException extends RuntimeException {
    public InvalidDishCategoryException(String message) {
        super(message);
    }
}

