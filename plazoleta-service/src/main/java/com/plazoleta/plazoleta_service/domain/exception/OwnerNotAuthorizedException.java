package com.plazoleta.plazoleta_service.domain.exception;

public class OwnerNotAuthorizedException extends RuntimeException {
    public OwnerNotAuthorizedException(String message) {
        super(message);
    }
}


