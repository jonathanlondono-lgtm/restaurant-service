package com.plazoleta.plazoleta_service.domain.exception;

public class NoPendingOrderToCancelException extends RuntimeException {
    public NoPendingOrderToCancelException(String message) {
        super(message);
    }
}
