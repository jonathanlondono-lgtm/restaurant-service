package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.port.out.*;
import com.plazoleta.plazoleta_service.domain.model.Pedido;
import com.plazoleta.plazoleta_service.domain.model.PedidoDetalle;
import com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter.dto.OrderEventDto;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.OrderCreateRequestDto;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.DishQuantityDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderCreateUseCaseTest {
    @Mock
    private TokenServicePort tokenServicePort;
    @Mock
    private RestaurantListQueryPersistencePort restaurantListQueryPersistencePort;
    @Mock
    private DishListQueryPersistencePort dishListQueryPersistencePort;
    @Mock
    private OrderQueryPort orderQueryPort;
    @Mock
    private OrderCommandPort orderCommandPort;
    @Mock
    private EventPublisherPort eventPublisherPort;

    @InjectMocks
    private OrderCreateUseCase orderCreateUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_success() {
        String token = "Bearer validtoken";
        Long clientId = 1L;
        Long restaurantId = 2L;
        List<DishQuantityDto> dishes = List.of(new DishQuantityDto(10L, 2));
        OrderCreateRequestDto requestDto = new OrderCreateRequestDto(restaurantId, dishes);

        when(tokenServicePort.extractUserId(token)).thenReturn(clientId);
        when(restaurantListQueryPersistencePort.getAllRestaurants()).thenReturn(List.of(
                // Simula restaurante existente
                new com.plazoleta.plazoleta_service.domain.model.Restaurante(restaurantId, "Rest", "Dir", "Tel", "Logo", "Nit", LocalDateTime.now(), LocalDateTime.now())
        ));
        Page<com.plazoleta.plazoleta_service.domain.model.Plato> platosPage = new PageImpl<>(List.of(
                new com.plazoleta.plazoleta_service.domain.model.Plato(10L, "Pizza", null, "desc", "url", "cat", true, restaurantId)
        ));
        when(dishListQueryPersistencePort.getDishesByRestaurant(eq(restaurantId), any(), anyInt(), anyInt()))
                .thenReturn(platosPage);
        when(orderQueryPort.hasActiveOrder(clientId)).thenReturn(false);
        Pedido pedidoGuardado = new Pedido(100L, clientId, restaurantId, "PENDIENTE", null, LocalDateTime.now(), LocalDateTime.now(), null,
                List.of(new PedidoDetalle(null, null, 10L, 2)));
        when(orderCommandPort.saveOrder(any())).thenReturn(pedidoGuardado);

        orderCreateUseCase.createOrder(requestDto, token);

        ArgumentCaptor<OrderEventDto> eventCaptor = ArgumentCaptor.forClass(OrderEventDto.class);
        verify(eventPublisherPort).publishOrderEvent(eventCaptor.capture());
        OrderEventDto event = eventCaptor.getValue();
        assertEquals(100L, event.getPedidoId());
        assertEquals(clientId, event.getClienteId());
        assertEquals(restaurantId, event.getRestauranteId());
        assertEquals("PENDIENTE", event.getEstado());
        assertEquals("plazoleta-service", event.getFuente());
    }
}
