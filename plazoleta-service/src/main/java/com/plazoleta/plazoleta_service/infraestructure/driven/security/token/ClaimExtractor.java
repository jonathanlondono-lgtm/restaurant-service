package com.plazoleta.plazoleta_service.infraestructure.driven.security.token;

public interface ClaimExtractor<T> {
    T extract(String bearerToken, String jwtSecret);
}
