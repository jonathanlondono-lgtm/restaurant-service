package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.application.port.out.OrderQueryPort;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderQueryAdapter implements OrderQueryPort {
    private final OrderRepository orderRepository;

    @Override
    public boolean hasActiveOrder(Long clientId) {
        List<String> activeStates = List.of("PENDIENTE", "EN_PREPARACION", "LISTO");
        return orderRepository.existsByClienteIdAndEstadoIn(clientId, activeStates);
    }
}

