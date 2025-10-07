package com.plazoleta.plazoleta_service.application.port.out;

import com.plazoleta.plazoleta_service.domain.model.Pedido;

public interface OrderQueryPort {
    boolean hasActiveOrder(Long clientId);
    Pedido findPendingOrderByClientId(Long clientId);
    Pedido findActiveOrderByClientId(Long clientId);
}