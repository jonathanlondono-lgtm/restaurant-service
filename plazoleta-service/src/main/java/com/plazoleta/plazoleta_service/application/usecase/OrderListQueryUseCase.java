package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.port.in.OrderListQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.OrderListQueryPersistencePort;
import com.plazoleta.plazoleta_service.application.port.out.DishQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.TokenServicePort;
import com.plazoleta.plazoleta_service.domain.model.Pedido;
import com.plazoleta.plazoleta_service.application.dto.OrderDetailResponseDto;
import com.plazoleta.plazoleta_service.application.dto.OrderPageResponseDto;
import com.plazoleta.plazoleta_service.application.dto.OrderResponseDto;
import com.plazoleta.plazoleta_service.domain.exception.InvalidOrderDetailDishIdException;
import com.plazoleta.plazoleta_service.domain.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderListQueryUseCase implements OrderListQueryPort {
    private final OrderListQueryPersistencePort orderListQueryPersistencePort;
    private final DishQueryPort dishQueryPort;
    private final TokenServicePort tokenServicePort;


    @Override
    public OrderPageResponseDto getOrdersByRestaurant(String bearerToken, String estado, int page, int size) {
        Long restauranteId = tokenServicePort.extractRestaurantId(bearerToken);
        Page<Pedido> pedidoPage = orderListQueryPersistencePort.getOrdersByRestaurant(restauranteId, estado, page, size);
        List<OrderResponseDto> orders = pedidoPage.getContent().stream()
                .map(pedido -> new OrderResponseDto(
                        pedido.getId(),
                        pedido.getEstado(),
                        pedido.getFechaCreacion().toString(),
                        pedido.getClienteId(),
                        pedido.getDetalles().stream()
                                .map(detalle -> {
                                    if (detalle.getPlatoId() == null) {
                                        throw new InvalidOrderDetailDishIdException(ExceptionMessages.INVALID_ORDER_DETAIL_DISH_ID + " OrderId: " + pedido.getId());
                                    }
                                    var dishName = dishQueryPort.getDishNameByIdAndRestaurant(detalle.getPlatoId(), restauranteId);
                                    return new OrderDetailResponseDto(
                                            detalle.getId(),
                                            detalle.getPlatoId(),
                                            dishName,
                                            detalle.getCantidad()
                                    );
                                })
                                .toList()
                ))
                .toList();
        return new OrderPageResponseDto(
                orders,
                pedidoPage.getNumber(),
                pedidoPage.getSize(),
                pedidoPage.getTotalElements(),
                pedidoPage.getTotalPages()
        );
    }
}
