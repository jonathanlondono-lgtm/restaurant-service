package com.plazoleta.plazoleta_service.domain.exception;

public class InvalidOrderDetailDishIdException extends RuntimeException {
    public InvalidOrderDetailDishIdException(String message) {
        super(message);
    }
}

