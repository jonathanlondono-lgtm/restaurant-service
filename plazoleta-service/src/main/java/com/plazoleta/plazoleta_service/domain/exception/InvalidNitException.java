package com.plazoleta.plazoleta_service.domain.exception;

public class InvalidNitException extends RuntimeException {
    public InvalidNitException(String message) {
        super(message);
    }
}