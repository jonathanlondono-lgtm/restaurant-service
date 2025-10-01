package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.domain.model.Pedido;
import com.plazoleta.plazoleta_service.domain.model.PedidoDetalle;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.OrderEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.OrderEntityMapper;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderCommandAdapterTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderEntityMapper orderEntityMapper;

    @InjectMocks
    private OrderCommandAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveOrder_success() {
        Pedido pedido = new Pedido(null, 1L, 2L, "PENDIENTE", null, LocalDateTime.now(), LocalDateTime.now(), null,
                List.of(new PedidoDetalle(null, null, 10L, 2)));
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(100L);
        orderEntity.setClienteId(1L);
        orderEntity.setEstado("PENDIENTE");
        orderEntity.setFechaCreacion(LocalDateTime.now());
        orderEntity.setFechaActualizacion(LocalDateTime.now());
        orderEntity.setRestaurante(new com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantEntity());
        when(orderEntityMapper.toEntity(pedido)).thenReturn(orderEntity);
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);

        Pedido result = adapter.saveOrder(pedido);
        assertEquals(100L, result.getId());
        verify(orderRepository, times(1)).save(any(OrderEntity.class));
    }
}
