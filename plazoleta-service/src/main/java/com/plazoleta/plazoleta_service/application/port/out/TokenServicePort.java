package com.plazoleta.plazoleta_service.application.port.out;

public interface TokenServicePort {
    Long extractUserId(String bearerToken);
}

