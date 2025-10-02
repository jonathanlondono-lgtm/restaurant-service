package com.plazoleta.plazoleta_service.application.port.out;

import com.plazoleta.plazoleta_service.domain.model.Pedido;
import org.springframework.data.domain.Page;

public interface OrderListQueryPersistencePort {
    Page<Pedido> getOrdersByRestaurant(Long restauranteId, String estado, int page, int size);
}

