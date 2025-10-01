package com.plazoleta.plazoleta_service.application.port.out;

public interface OrderQueryPort {
    boolean hasActiveOrder(Long clientId);
}