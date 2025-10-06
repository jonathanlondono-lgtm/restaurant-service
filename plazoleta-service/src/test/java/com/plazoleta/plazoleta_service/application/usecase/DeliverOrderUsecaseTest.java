package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.dto.DeliverOrderRequestDto;
import com.plazoleta.plazoleta_service.application.port.out.OrderAssignQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.OrderCommandPort;
import com.plazoleta.plazoleta_service.application.port.out.TokenServicePort;
import com.plazoleta.plazoleta_service.domain.exception.InvalidSecurityPinException;
import com.plazoleta.plazoleta_service.domain.exception.OrderNotFoundOrNotInPreparationStateException;
import com.plazoleta.plazoleta_service.domain.model.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliverOrderUsecaseTest {
    @Mock
    private OrderAssignQueryPort orderAssignQueryPort;
    @Mock
    private OrderCommandPort orderCommandPort;
    @Mock
    private TokenServicePort tokenServicePort;

    @InjectMocks
    private DeliverOrderUsecase deliverOrderUsecase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deliverOrder_success() {
        // Given
        String bearerToken = "Bearer validtoken";
        Long restauranteId = 5L;
        Long orderId = 100L;
        String correctPin = "1234";
        DeliverOrderRequestDto requestDto = new DeliverOrderRequestDto(orderId, correctPin);

        Pedido pedido = new Pedido();
        pedido.setId(orderId);
        pedido.setRestauranteId(restauranteId);
        pedido.setEstado("LISTO");
        pedido.setPinSeguridad(correctPin);

        when(tokenServicePort.extractRestaurantId(bearerToken)).thenReturn(restauranteId);
        when(orderAssignQueryPort.findByIdAndRestauranteIdAndEstado(orderId, restauranteId, "LISTO")).thenReturn(pedido);

        assertDoesNotThrow(() -> deliverOrderUsecase.deliverOrder(requestDto, bearerToken));

        assertEquals("ENTREGADO", pedido.getEstado());
        assertNotNull(pedido.getFechaActualizacion());
        verify(orderCommandPort).updateOrder(pedido);
    }

    @Test
    void deliverOrder_orderNotFound_throwsException() {
        // Given
        String bearerToken = "Bearer validtoken";
        Long restauranteId = 5L;
        Long orderId = 100L;
        String pin = "1234";
        DeliverOrderRequestDto requestDto = new DeliverOrderRequestDto(orderId, pin);

        when(tokenServicePort.extractRestaurantId(bearerToken)).thenReturn(restauranteId);
        when(orderAssignQueryPort.findByIdAndRestauranteIdAndEstado(orderId, restauranteId, "LISTO")).thenReturn(null);

        assertThrows(OrderNotFoundOrNotInPreparationStateException.class,
                    () -> deliverOrderUsecase.deliverOrder(requestDto, bearerToken));
        verify(orderCommandPort, never()).updateOrder(any());
    }

    @Test
    void deliverOrder_wrongPin_throwsException() {
        // Given
        String bearerToken = "Bearer validtoken";
        Long restauranteId = 5L;
        Long orderId = 100L;
        String correctPin = "1234";
        String wrongPin = "9999";
        DeliverOrderRequestDto requestDto = new DeliverOrderRequestDto(orderId, wrongPin);

        Pedido pedido = new Pedido();
        pedido.setId(orderId);
        pedido.setRestauranteId(restauranteId);
        pedido.setEstado("LISTO");
        pedido.setPinSeguridad(correctPin);

        when(tokenServicePort.extractRestaurantId(bearerToken)).thenReturn(restauranteId);
        when(orderAssignQueryPort.findByIdAndRestauranteIdAndEstado(orderId, restauranteId, "LISTO")).thenReturn(pedido);

        // When & Then
        assertThrows(InvalidSecurityPinException.class,
                    () -> deliverOrderUsecase.deliverOrder(requestDto, bearerToken));
        verify(orderCommandPort, never()).updateOrder(any());
    }

    @Test
    void deliverOrder_nullRestaurantId_throwsException() {
        // Given
        String bearerToken = "Bearer invalidtoken";
        Long orderId = 100L;
        String pin = "1234";
        DeliverOrderRequestDto requestDto = new DeliverOrderRequestDto(orderId, pin);

        when(tokenServicePort.extractRestaurantId(bearerToken)).thenReturn(null);
        when(orderAssignQueryPort.findByIdAndRestauranteIdAndEstado(orderId, null, "LISTO")).thenReturn(null);

        // When & Then
        assertThrows(OrderNotFoundOrNotInPreparationStateException.class,
                    () -> deliverOrderUsecase.deliverOrder(requestDto, bearerToken));
        verify(orderCommandPort, never()).updateOrder(any());
    }
}
