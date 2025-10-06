package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.dto.OrderAssignRequestDto;
import com.plazoleta.plazoleta_service.application.port.out.*;
import com.plazoleta.plazoleta_service.domain.exception.OrderNotFoundOrNotInPreparationStateException;
import com.plazoleta.plazoleta_service.domain.model.Pedido;
import com.plazoleta.plazoleta_service.domain.service.PinGeneratorService;
import com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter.dto.OrderEventDto;
import com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter.dto.OrderReadyNotificationEventDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderReadyUseCaseTest {
    @Mock
    private OrderAssignQueryPort orderAssignQueryPort;
    @Mock
    private OrderCommandPort orderCommandPort;
    @Mock
    private TokenServicePort tokenServicePort;
    @Mock
    private EventPublisherPort eventPublisherPort;
    @Mock
    private OrderReadyNotificationPublisherPort orderReadyNotificationPublisherPort;
    @Mock
    private PinGeneratorService pinGeneratorService;

    @InjectMocks
    private OrderReadyUseCase orderReadyUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void markOrderAsReady_success() {
        OrderAssignRequestDto requestDto = new OrderAssignRequestDto();
        requestDto.setOrderId(1L);
        String bearerToken = "Bearer token";
        Long empleadoId = 10L;
        Long restauranteId = 20L;
        String pin = "1234";
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setClienteId(2L);
        pedido.setRestauranteId(restauranteId);
        pedido.setEstado("EN_PREPARACION");
        pedido.setPhone("+573001234567");

        when(tokenServicePort.extractUserId(bearerToken)).thenReturn(empleadoId);
        when(tokenServicePort.extractRestaurantId(bearerToken)).thenReturn(restauranteId);
        when(pinGeneratorService.generatePin()).thenReturn(pin);
        when(orderAssignQueryPort.findByIdAndRestauranteIdAndEstado(1L, restauranteId, "EN_PREPARACION")).thenReturn(pedido);

        // Capturadores para verificar los eventos enviados
        ArgumentCaptor<OrderEventDto> eventCaptor = ArgumentCaptor.forClass(OrderEventDto.class);
        ArgumentCaptor<OrderReadyNotificationEventDto> notificationCaptor = ArgumentCaptor.forClass(OrderReadyNotificationEventDto.class);

        orderReadyUseCase.markOrderAsReady(requestDto, bearerToken);

        verify(orderCommandPort).updateOrder(any(Pedido.class));
        verify(eventPublisherPort).publishOrderEvent(eventCaptor.capture());
        verify(orderReadyNotificationPublisherPort).publishOrderReadyNotification(notificationCaptor.capture());

        OrderEventDto eventDto = eventCaptor.getValue();
        assertEquals(pedido.getId(), eventDto.getPedidoId());
        assertEquals(pedido.getClienteId(), eventDto.getClienteId());
        assertEquals(pedido.getRestauranteId(), eventDto.getRestauranteId());
        assertEquals("LISTO", eventDto.getEstado());
        assertEquals(empleadoId, eventDto.getEmpleadoId());

        OrderReadyNotificationEventDto notificationDto = notificationCaptor.getValue();
        assertEquals(pedido.getId(), notificationDto.getPedidoId());
        assertEquals(pedido.getClienteId(), notificationDto.getClienteId());
        assertEquals(pedido.getRestauranteId(), notificationDto.getRestauranteId());
        assertEquals("LISTO", notificationDto.getMensaje());
        assertEquals(pin, notificationDto.getPin());
        assertEquals(pedido.getPhone(), notificationDto.getTelefonoCliente());
    }

    @Test
    void markOrderAsReady_orderNotFound_throwsException() {
        OrderAssignRequestDto requestDto = new OrderAssignRequestDto();
        requestDto.setOrderId(1L);
        String bearerToken = "Bearer token";
        Long restauranteId = 20L;
        when(tokenServicePort.extractUserId(bearerToken)).thenReturn(10L);
        when(tokenServicePort.extractRestaurantId(bearerToken)).thenReturn(restauranteId);
        when(pinGeneratorService.generatePin()).thenReturn("1234");
        when(orderAssignQueryPort.findByIdAndRestauranteIdAndEstado(1L, restauranteId, "EN_PREPARACION")).thenReturn(null);

        OrderNotFoundOrNotInPreparationStateException exception = assertThrows(OrderNotFoundOrNotInPreparationStateException.class,
                () -> orderReadyUseCase.markOrderAsReady(requestDto, bearerToken));
        assertEquals("Order not found or not in PREPARATION state for this restaurant.", exception.getMessage());
    }
}

