package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.dto.OrderAssignRequestDto;
import com.plazoleta.plazoleta_service.application.port.in.OrderAssignUseCasePort;
import com.plazoleta.plazoleta_service.application.port.out.OrderAssignQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.OrderCommandPort;
import com.plazoleta.plazoleta_service.application.port.out.TokenServicePort;
import com.plazoleta.plazoleta_service.application.port.out.EventPublisherPort;
import com.plazoleta.plazoleta_service.domain.exception.OrderNotFoundOrNotInPreparationStateException;
import com.plazoleta.plazoleta_service.domain.model.Pedido;
import com.plazoleta.plazoleta_service.domain.util.ExceptionMessages;
import com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter.dto.OrderEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class OrderAssignUseCase implements OrderAssignUseCasePort {
    private final OrderAssignQueryPort orderAssignQueryPort;
    private final OrderCommandPort orderCommandPort;
    private final TokenServicePort tokenServicePort;
    private final EventPublisherPort eventPublisherPort;

    @Override
    public void assignOrderToEmployee(OrderAssignRequestDto requestDto, String bearerToken) {
        Long empleadoId = tokenServicePort.extractUserId(bearerToken);
        Long restauranteId = tokenServicePort.extractRestaurantId(bearerToken);
        Long orderId = requestDto.getOrderId();

        Pedido pedido = orderAssignQueryPort.findByIdAndRestauranteIdAndEstado(orderId, restauranteId, "PENDIENTE");
        if (pedido == null) {
            throw new OrderNotFoundOrNotInPreparationStateException(ExceptionMessages.ORDER_NOT_FOUND_OR_NOT_IN_PREPARATION);
        }

        pedido.setEmpleadoAsignadoId(empleadoId);
        pedido.setEstado("EN_PREPARACION");

        pedido.setFechaActualizacion(LocalDateTime.now());

        orderCommandPort.updateOrder(pedido);

        String fecha = DateTimeFormatter.ISO_INSTANT
                .withZone(ZoneOffset.UTC)
                .format(pedido.getFechaActualizacion().atZone(ZoneId.systemDefault()).toInstant());


        OrderEventDto eventDto = new OrderEventDto(
                pedido.getId(),
                pedido.getClienteId(),
                pedido.getRestauranteId(),
                pedido.getEstado(),
                fecha,
                "plazoleta-service",
                empleadoId,
                null
        );
        eventPublisherPort.publishOrderEvent(eventDto);
    }
}

