package com.plazoleta.plazoleta_service.domain.exception;

public class ClientHasActiveOrderException extends RuntimeException {
    public ClientHasActiveOrderException(String message) {
        super(message);
    }
}


