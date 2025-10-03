package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.application.port.out.OrderAssignQueryPort;
import com.plazoleta.plazoleta_service.domain.model.Pedido;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.OrderEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.OrderEntityMapper;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderAssignQueryAdapter implements OrderAssignQueryPort {
    private final OrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;

    @Override
    public Pedido findByIdAndRestauranteIdAndEstado(Long orderId, Long restauranteId, String estado) {
        OrderEntity entity = orderRepository.findByIdAndRestaurante_IdAndEstado(orderId, restauranteId, estado);
        return orderEntityMapper.toDomain(entity);
    }
}

