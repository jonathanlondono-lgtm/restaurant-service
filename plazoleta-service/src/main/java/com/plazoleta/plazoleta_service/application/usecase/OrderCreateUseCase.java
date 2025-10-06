package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.port.in.OrderCreateUseCasePort;
import com.plazoleta.plazoleta_service.application.port.out.*;
import com.plazoleta.plazoleta_service.domain.model.Pedido;
import com.plazoleta.plazoleta_service.domain.model.PedidoDetalle;
import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter.dto.OrderEventDto;
import com.plazoleta.plazoleta_service.application.dto.OrderCreateRequestDto;
import com.plazoleta.plazoleta_service.application.dto.DishQuantityDto;
import com.plazoleta.plazoleta_service.domain.exception.RestaurantNotFoundException;
import com.plazoleta.plazoleta_service.domain.exception.DishNotFoundInRestaurantException;
import com.plazoleta.plazoleta_service.domain.exception.ClientHasActiveOrderException;
import com.plazoleta.plazoleta_service.domain.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderCreateUseCase implements OrderCreateUseCasePort {
    private final TokenServicePort tokenServicePort;
    private final RestaurantListQueryPersistencePort restaurantListQueryPersistencePort;
    private final DishListQueryPersistencePort dishListQueryPersistencePort;
    private final OrderQueryPort orderQueryPort;
    private final OrderCommandPort orderCommandPort;
    private final EventPublisherPort eventPublisherPort;

    @Override
    public void createOrder(OrderCreateRequestDto orderCreateRequestDto, String bearerToken) {
        Long clientId = tokenServicePort.extractUserId(bearerToken);
        Long restaurantId = orderCreateRequestDto.getRestaurantId();
        String phone= tokenServicePort.extraxtPhoneNumber(bearerToken);
        List<DishQuantityDto> dishes = orderCreateRequestDto.getDishes();

        // Validar restaurante
        boolean restaurantExists = restaurantListQueryPersistencePort.getAllRestaurants()
                .stream().anyMatch(r -> r.getId().equals(restaurantId));
        if (!restaurantExists) {
            throw new RestaurantNotFoundException(ExceptionMessages.RESTAURANT_NOT_FOUND);
        }

        // Validar platos
        List<Long> dishIds = dishes.stream().map(DishQuantityDto::getDishId).toList();
        List<Long> validDishIds = dishListQueryPersistencePort.getDishesByRestaurant(restaurantId, null, 0, Integer.MAX_VALUE)
                .stream().map(Plato::getId).toList();
        if (!validDishIds.containsAll(dishIds)) {
            throw new DishNotFoundInRestaurantException(ExceptionMessages.DISH_NOT_FOUND_IN_RESTAURANT);
        }

        // Validar pedidos en curso
        if (orderQueryPort.hasActiveOrder(clientId)) {
            throw new ClientHasActiveOrderException(ExceptionMessages.CLIENT_HAS_ACTIVE_ORDER);
        }

        // Mapear a dominio
        List<PedidoDetalle> detalles = dishes.stream()
                .map(d -> new PedidoDetalle(null, null, d.getDishId(), d.getQuantity()))
                .toList();
        Pedido pedido = new Pedido(null, clientId, restaurantId, "PENDIENTE", null,
                LocalDateTime.now(), LocalDateTime.now(), null, detalles,phone);

        // Persistir pedido
        Pedido savedPedido = orderCommandPort.saveOrder(pedido);

        // Construir y publicar evento genérico
        String fechaEvento = DateTimeFormatter.ISO_INSTANT.format(savedPedido.getFechaCreacion().atZone(ZoneOffset.UTC));
        OrderEventDto event = new OrderEventDto(
            savedPedido.getId(),
            clientId,
            restaurantId,
            savedPedido.getEstado(),
            fechaEvento,
            "plazoleta-service",
            null,
            null
        );
        eventPublisherPort.publishOrderEvent(event);
    }
}
