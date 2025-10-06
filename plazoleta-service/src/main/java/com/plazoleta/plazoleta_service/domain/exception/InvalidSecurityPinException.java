package com.plazoleta.plazoleta_service.domain.exception;

public class InvalidSecurityPinException extends RuntimeException {
    public InvalidSecurityPinException(String message) {
        super(message);
    }
}
