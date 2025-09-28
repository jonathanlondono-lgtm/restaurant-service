package com.plazoleta.plazoleta_service.infraestructure.driven.rest.exception;

public class UserClientException extends RuntimeException {
    public UserClientException(String message, Throwable cause) {
        super(message, cause);
    }
    public UserClientException(String message) {
        super(message);
    }
}

