package com.plazoleta.plazoleta_service.domain.exception;

public class OrderNotFoundOrNotInPreparationStateException extends RuntimeException {
    public OrderNotFoundOrNotInPreparationStateException(String message) {
        super(message);
    }
}
