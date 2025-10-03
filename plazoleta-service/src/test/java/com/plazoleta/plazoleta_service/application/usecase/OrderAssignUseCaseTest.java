package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.dto.OrderAssignRequestDto;
import com.plazoleta.plazoleta_service.application.port.out.OrderAssignQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.OrderCommandPort;
import com.plazoleta.plazoleta_service.application.port.out.TokenServicePort;
import com.plazoleta.plazoleta_service.application.port.out.EventPublisherPort;
import com.plazoleta.plazoleta_service.domain.model.Pedido;
import com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter.dto.OrderEventDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderAssignUseCaseTest {
    @Mock
    private OrderAssignQueryPort orderAssignQueryPort;
    @Mock
    private OrderCommandPort orderCommandPort;
    @Mock
    private TokenServicePort tokenServicePort;
    @Mock
    private EventPublisherPort eventPublisherPort;

    @InjectMocks
    private OrderAssignUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void assignOrderToEmployee_success() {
        OrderAssignRequestDto requestDto = new OrderAssignRequestDto();
        requestDto.setOrderId(1L);
        String token = "Bearer token";
        Long empleadoId = 36L;
        Long restauranteId = 9L;
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setRestauranteId(restauranteId);
        pedido.setEstado("PENDIENTE");
        pedido.setClienteId(100L);

        when(tokenServicePort.extractUserId(token)).thenReturn(empleadoId);
        when(tokenServicePort.extractRestaurantId(token)).thenReturn(restauranteId);
        when(orderAssignQueryPort.findByIdAndRestauranteIdAndEstado(1L, restauranteId, "PENDIENTE")).thenReturn(pedido);

        assertDoesNotThrow(() -> useCase.assignOrderToEmployee(requestDto, token));
        verify(orderCommandPort).updateOrder(any(Pedido.class));
        verify(eventPublisherPort).publishOrderEvent(any(OrderEventDto.class));
    }

    @Test
    void assignOrderToEmployee_orderNotFound_throwsException() {
        OrderAssignRequestDto requestDto = new OrderAssignRequestDto();
        requestDto.setOrderId(1L);
        String token = "Bearer token";
        Long empleadoId = 36L;
        Long restauranteId = 9L;

        when(tokenServicePort.extractUserId(token)).thenReturn(empleadoId);
        when(tokenServicePort.extractRestaurantId(token)).thenReturn(restauranteId);
        when(orderAssignQueryPort.findByIdAndRestauranteIdAndEstado(1L, restauranteId, "PENDIENTE")).thenReturn(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.assignOrderToEmployee(requestDto, token));
        assertEquals("Order not found or not in PENDING state for this restaurant.", ex.getMessage());
    }
}

