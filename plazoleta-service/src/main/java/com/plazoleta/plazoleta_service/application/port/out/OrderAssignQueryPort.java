package com.plazoleta.plazoleta_service.application.port.out;

import com.plazoleta.plazoleta_service.domain.model.Pedido;

public interface OrderAssignQueryPort {
    Pedido findByIdAndRestauranteIdAndEstado(Long orderId, Long restauranteId, String estado);
}

