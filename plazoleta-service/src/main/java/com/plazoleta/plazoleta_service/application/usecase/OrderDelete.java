package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.port.in.CancelOrderServicePort;
import com.plazoleta.plazoleta_service.application.port.out.OrderQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.OrderCommandPort;
import com.plazoleta.plazoleta_service.application.port.out.TokenServicePort;
import com.plazoleta.plazoleta_service.application.port.out.EventPublisherPort;
import com.plazoleta.plazoleta_service.domain.exception.NoPendingOrderToCancelException;
import com.plazoleta.plazoleta_service.domain.exception.OrderCannotBeCancelledException;
import com.plazoleta.plazoleta_service.domain.model.Pedido;
import com.plazoleta.plazoleta_service.domain.util.ExceptionMessages;
import com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter.dto.OrderEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class OrderDelete implements CancelOrderServicePort {
    private final TokenServicePort tokenServicePort;
    private final OrderQueryPort orderQueryPort;
    private final OrderCommandPort orderCommandPort;
    private final EventPublisherPort eventPublisherPort;

    @Override
    public void cancelOrder(String bearerToken) {
        Long clientId = tokenServicePort.extractUserId(bearerToken);

        // Buscar si el cliente tiene alguna orden activa
        Pedido pedido = orderQueryPort.findActiveOrderByClientId(clientId);
        if (pedido == null) {
            throw new NoPendingOrderToCancelException(ExceptionMessages.NO_PENDING_ORDER_TO_CANCEL);
        }

        // Validar que el pedido esté en estado PENDIENTE (solo estos se pueden cancelar)
        if (!"PENDIENTE".equals(pedido.getEstado())) {
            throw new OrderCannotBeCancelledException(ExceptionMessages.ORDER_CANNOT_BE_CANCELLED);
        }

        // Cambiar estado a CANCELADO
        pedido.setEstado("CANCELADO");
        pedido.setFechaActualizacion(LocalDateTime.now());
        orderCommandPort.updateOrder(pedido);

        // Enviar evento de trazabilidad
        String fecha = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC).format(Instant.now());
        OrderEventDto eventDto = new OrderEventDto(
                pedido.getId(),
                pedido.getClienteId(),
                pedido.getRestauranteId(),
                pedido.getEstado(),
                fecha,
                "plazoleta-service",
                null,
                null
        );
        eventPublisherPort.publishOrderEvent(eventDto);
    }
}
