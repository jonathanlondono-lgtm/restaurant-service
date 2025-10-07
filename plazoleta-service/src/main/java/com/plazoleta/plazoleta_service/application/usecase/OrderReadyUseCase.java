package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.dto.OrderAssignRequestDto;
import com.plazoleta.plazoleta_service.application.port.in.OrderReadyUseCasePort;
import com.plazoleta.plazoleta_service.application.port.out.*;
import com.plazoleta.plazoleta_service.domain.exception.OrderNotFoundOrNotInPreparationStateException;
import com.plazoleta.plazoleta_service.domain.model.Pedido;
import com.plazoleta.plazoleta_service.domain.service.PinGeneratorService;
import com.plazoleta.plazoleta_service.domain.util.ExceptionMessages;
import com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter.dto.OrderEventDto;
import com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter.dto.OrderReadyNotificationEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class OrderReadyUseCase implements OrderReadyUseCasePort {
    private final OrderAssignQueryPort orderAssignQueryPort;
    private final OrderCommandPort orderCommandPort;
    private final TokenServicePort tokenServicePort;
    private final EventPublisherPort eventPublisherPort;
    private  final OrderReadyNotificationPublisherPort orderReadyNotificationPublisherPort;
    private final PinGeneratorService pinGeneratorService;



    @Override
    public void markOrderAsReady(OrderAssignRequestDto requestDto, String bearerToken) {
        Long empleadoId = tokenServicePort.extractUserId(bearerToken);
        Long restauranteId = tokenServicePort.extractRestaurantId(bearerToken);
        Long orderId = requestDto.getOrderId();
        String pin = pinGeneratorService.generatePin();

        Pedido pedido = orderAssignQueryPort.findByIdAndRestauranteIdAndEstado(orderId, restauranteId, "EN_PREPARACION");
        if (pedido == null) {
            throw new OrderNotFoundOrNotInPreparationStateException(ExceptionMessages.ORDER_NOT_FOUND_OR_NOT_IN_PREPARATION);
        }

        pedido.setEstado("LISTO");
        pedido.setFechaActualizacion(LocalDateTime.now());
        pedido.setPinSeguridad(pin);
        orderCommandPort.updateOrder(pedido);

        String fecha = DateTimeFormatter.ISO_INSTANT
                .format(pedido.getFechaActualizacion().atZone(ZoneOffset.UTC));


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

        OrderReadyNotificationEventDto orderReadyNotificationEventDto = new OrderReadyNotificationEventDto(
                pedido.getId(),
                pedido.getClienteId(),
                pedido.getRestauranteId(),
                fecha,
                "LISTO",
                pin,
                pedido.getPhone()
        );
        orderReadyNotificationPublisherPort.publishOrderReadyNotification(orderReadyNotificationEventDto);

    }
}
