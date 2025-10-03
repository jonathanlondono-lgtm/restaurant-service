package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.domain.model.Pedido;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.OrderEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.OrderEntityMapper;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderAssignQueryAdapterTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderEntityMapper orderEntityMapper;
    @InjectMocks
    private OrderAssignQueryAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByIdAndRestauranteIdAndEstado_found_returnsPedido() {
        Long orderId = 1L;
        Long restauranteId = 9L;
        String estado = "PENDIENTE";
        OrderEntity entity = new OrderEntity();
        Pedido pedido = new Pedido();
        when(orderRepository.findByIdAndRestaurante_IdAndEstado(orderId, restauranteId, estado)).thenReturn(entity);
        when(orderEntityMapper.toDomain(entity)).thenReturn(pedido);

        Pedido result = adapter.findByIdAndRestauranteIdAndEstado(orderId, restauranteId, estado);
        assertNotNull(result);
        assertEquals(pedido, result);
    }

    @Test
    void findByIdAndRestauranteIdAndEstado_notFound_returnsNull() {
        Long orderId = 1L;
        Long restauranteId = 9L;
        String estado = "PENDIENTE";
        when(orderRepository.findByIdAndRestaurante_IdAndEstado(orderId, restauranteId, estado)).thenReturn(null);

        Pedido result = adapter.findByIdAndRestauranteIdAndEstado(orderId, restauranteId, estado);
        assertNull(result);
    }
}
