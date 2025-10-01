package com.plazoleta.plazoleta_service.application.port.out;

import com.plazoleta.plazoleta_service.domain.model.Pedido;

public interface OrderCommandPort {
    Pedido saveOrder(Pedido pedido);
}

