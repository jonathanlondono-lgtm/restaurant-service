package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.port.out.OrderListQueryPersistencePort;
import com.plazoleta.plazoleta_service.application.port.out.DishQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.TokenServicePort;
import com.plazoleta.plazoleta_service.domain.exception.InvalidOrderDetailDishIdException;
import com.plazoleta.plazoleta_service.domain.model.Pedido;
import com.plazoleta.plazoleta_service.domain.model.PedidoDetalle;
import com.plazoleta.plazoleta_service.application.dto.OrderPageResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderListQueryUseCaseTest {
    @Mock
    private OrderListQueryPersistencePort orderListQueryPersistencePort;
    @Mock
    private DishQueryPort dishQueryPort;
    @Mock
    private TokenServicePort tokenServicePort;

    @InjectMocks
    private OrderListQueryUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getOrdersByRestaurant_success() {
        String token = "Bearer token";
        Long restauranteId = 2L;
        String estado = "PENDIENTE";
        int page = 0, size = 1;

        PedidoDetalle detalle = new PedidoDetalle();
        detalle.setId(1L);
        detalle.setPlatoId(10L);
        detalle.setCantidad(2);

        Pedido pedido = new Pedido();
        pedido.setId(7L);
        pedido.setEstado(estado);
        pedido.setFechaCreacion(LocalDateTime.now());
        pedido.setClienteId(5L);
        pedido.setDetalles(List.of(detalle));

        Page<Pedido> pedidoPage = new PageImpl<>(List.of(pedido), PageRequest.of(page, size), 1);

        when(tokenServicePort.extractRestaurantId(token)).thenReturn(restauranteId);
        when(orderListQueryPersistencePort.getOrdersByRestaurant(restauranteId, estado, page, size)).thenReturn(pedidoPage);
        when(dishQueryPort.getDishNameByIdAndRestaurant(10L, restauranteId)).thenReturn("Pizza");

        OrderPageResponseDto result = useCase.getOrdersByRestaurant(token, estado, page, size);

        assertEquals(1, result.getOrders().size());
        assertEquals(7L, result.getOrders().get(0).getId());
        assertEquals("Pizza", result.getOrders().get(0).getDetalles().get(0).getDishName());
        verify(orderListQueryPersistencePort).getOrdersByRestaurant(restauranteId, estado, page, size);
        verify(dishQueryPort).getDishNameByIdAndRestaurant(10L, restauranteId);
    }

    @Test
    void getOrdersByRestaurant_nullDishId_throwsException() {
        String token = "Bearer token";
        Long restauranteId = 2L;
        String estado = "PENDIENTE";
        int page = 0, size = 1;

        PedidoDetalle detalle = new PedidoDetalle();
        detalle.setId(1L);
        detalle.setPlatoId(null);
        detalle.setCantidad(2);

        Pedido pedido = new Pedido();
        pedido.setId(7L);
        pedido.setEstado(estado);
        pedido.setFechaCreacion(LocalDateTime.now());
        pedido.setClienteId(5L);
        pedido.setDetalles(List.of(detalle));

        Page<Pedido> pedidoPage = new PageImpl<>(List.of(pedido), PageRequest.of(page, size), 1);

        when(tokenServicePort.extractRestaurantId(token)).thenReturn(restauranteId);
        when(orderListQueryPersistencePort.getOrdersByRestaurant(restauranteId, estado, page, size)).thenReturn(pedidoPage);

        Exception ex = assertThrows(InvalidOrderDetailDishIdException.class, () -> useCase.getOrdersByRestaurant(token, estado, page, size));
        assertTrue(ex.getMessage().contains("Dish ID in order detail is null for this order."));
    }
}
