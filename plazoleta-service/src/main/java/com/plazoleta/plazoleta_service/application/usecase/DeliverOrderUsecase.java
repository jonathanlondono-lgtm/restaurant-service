package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.dto.DeliverOrderRequestDto;
import com.plazoleta.plazoleta_service.application.port.in.DeliverOrderServicePort;
import com.plazoleta.plazoleta_service.application.port.out.OrderAssignQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.OrderCommandPort;
import com.plazoleta.plazoleta_service.application.port.out.TokenServicePort;
import com.plazoleta.plazoleta_service.domain.exception.InvalidSecurityPinException;
import com.plazoleta.plazoleta_service.domain.exception.OrderNotFoundOrNotInPreparationStateException;
import com.plazoleta.plazoleta_service.domain.model.Pedido;
import com.plazoleta.plazoleta_service.domain.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeliverOrderUsecase implements DeliverOrderServicePort {

    private final OrderAssignQueryPort orderAssignQueryPort;
    private final TokenServicePort tokenServicePort;
    private final OrderCommandPort orderCommandPort;



    @Override
    public void deliverOrder(DeliverOrderRequestDto request, String bearerToken) {
        Long restauranteId = tokenServicePort.extractRestaurantId(bearerToken);
        Long orderId = request.getOrderId();


        Pedido pedido = orderAssignQueryPort.findByIdAndRestauranteIdAndEstado(orderId, restauranteId, "LISTO");
        if (pedido == null) {
            throw new OrderNotFoundOrNotInPreparationStateException(ExceptionMessages.ORDER_NOT_FOUND_OR_NOT_IN_PREPARATION);
        }

        if (!pedido.getPinSeguridad().equals(request.getPinSeguridad())) {
            throw new InvalidSecurityPinException(ExceptionMessages.PIN_NOT_VALID);
        }

        pedido.setEstado("ENTREGADO");
        pedido.setFechaActualizacion(LocalDateTime.now());
        orderCommandPort.updateOrder(pedido);



    }
}
