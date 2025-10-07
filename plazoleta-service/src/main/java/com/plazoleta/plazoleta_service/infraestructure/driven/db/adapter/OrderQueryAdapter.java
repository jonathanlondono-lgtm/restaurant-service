package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.application.port.out.OrderQueryPort;
import com.plazoleta.plazoleta_service.domain.model.Pedido;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.OrderEntityMapper;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderQueryAdapter implements OrderQueryPort {
    private final OrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;

    @Override
    public boolean hasActiveOrder(Long clientId) {
        List<String> activeStates = List.of("PENDIENTE", "EN_PREPARACION", "LISTO");
        return orderRepository.existsByClienteIdAndEstadoIn(clientId, activeStates);
    }

    @Override
    public Pedido findPendingOrderByClientId(Long clientId) {
        return orderRepository.findByClienteIdAndEstado(clientId, "PENDIENTE")
                .map(orderEntityMapper::toDomain)
                .orElse(null);
    }

    @Override
    public Pedido findActiveOrderByClientId(Long clientId) {
        List<String> activeStates = List.of("PENDIENTE", "EN_PREPARACION", "LISTO");
        return orderRepository.findByClienteIdAndEstadoIn(clientId, activeStates)
                .map(orderEntityMapper::toDomain)
                .orElse(null);
    }
}
