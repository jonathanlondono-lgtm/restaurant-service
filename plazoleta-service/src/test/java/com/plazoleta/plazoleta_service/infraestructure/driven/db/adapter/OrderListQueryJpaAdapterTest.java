package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.domain.model.Pedido;
import com.plazoleta.plazoleta_service.domain.model.PedidoDetalle;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.OrderEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.OrderDetailEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.DishEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.OrderRepository;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.DishRepository;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.OrderEntityMapper;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.OrderDetailEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderListQueryJpaAdapterTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private DishRepository dishRepository;
    @Mock
    private OrderEntityMapper orderEntityMapper;
    @Mock
    private OrderDetailEntityMapper orderDetailEntityMapper;
    @InjectMocks
    private OrderListQueryJpaAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getOrdersByRestaurant_withEstado_success() {
        Long restauranteId = 1L;
        String estado = "PENDIENTE";
        int page = 0, size = 1;
        Pageable pageable = PageRequest.of(page, size);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(10L);
        OrderDetailEntity detailEntity = new OrderDetailEntity();
        detailEntity.setId(100L);
        DishEntity dishEntity = new DishEntity();
        dishEntity.setId(5L);
        detailEntity.setPlato(dishEntity);
        orderEntity.setDetalles(List.of(detailEntity));
        Page<OrderEntity> entityPage = new PageImpl<>(List.of(orderEntity), pageable, 1);
        when(orderRepository.findByRestaurante_IdAndEstado(restauranteId, estado, pageable)).thenReturn(entityPage);
        Pedido pedido = new Pedido();
        pedido.setId(10L);
        PedidoDetalle detalle = new PedidoDetalle();
        detalle.setId(100L);
        detalle.setPlatoId(5L);
        when(orderEntityMapper.toDomain(orderEntity)).thenReturn(pedido);
        when(orderDetailEntityMapper.toDomain(detailEntity)).thenReturn(detalle);
        Page<Pedido> result = adapter.getOrdersByRestaurant(restauranteId, estado, page, size);
        assertEquals(1, result.getTotalElements());
        assertEquals(10L, result.getContent().get(0).getId());
        assertEquals(100L, result.getContent().get(0).getDetalles().get(0).getId());
        assertEquals(5L, result.getContent().get(0).getDetalles().get(0).getPlatoId());
        verify(orderRepository).findByRestaurante_IdAndEstado(restauranteId, estado, pageable);
        verify(orderEntityMapper).toDomain(orderEntity);
        verify(orderDetailEntityMapper).toDomain(detailEntity);
    }

    @Test
    void getOrdersByRestaurant_withoutEstado_success() {
        Long restauranteId = 1L;
        String estado = null;
        int page = 0, size = 1;
        Pageable pageable = PageRequest.of(page, size);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(11L);
        OrderDetailEntity detailEntity = new OrderDetailEntity();
        detailEntity.setId(101L);
        DishEntity dishEntity = new DishEntity();
        dishEntity.setId(6L);
        detailEntity.setPlato(dishEntity);
        orderEntity.setDetalles(List.of(detailEntity));
        Page<OrderEntity> entityPage = new PageImpl<>(List.of(orderEntity), pageable, 1);
        when(orderRepository.findByRestaurante_Id(restauranteId, pageable)).thenReturn(entityPage);
        Pedido pedido = new Pedido();
        pedido.setId(11L);
        PedidoDetalle detalle = new PedidoDetalle();
        detalle.setId(101L);
        detalle.setPlatoId(6L);
        when(orderEntityMapper.toDomain(orderEntity)).thenReturn(pedido);
        when(orderDetailEntityMapper.toDomain(detailEntity)).thenReturn(detalle);
        Page<Pedido> result = adapter.getOrdersByRestaurant(restauranteId, estado, page, size);
        assertEquals(1, result.getTotalElements());
        assertEquals(11L, result.getContent().get(0).getId());
        assertEquals(101L, result.getContent().get(0).getDetalles().get(0).getId());
        assertEquals(6L, result.getContent().get(0).getDetalles().get(0).getPlatoId());
        verify(orderRepository).findByRestaurante_Id(restauranteId, pageable);
        verify(orderEntityMapper).toDomain(orderEntity);
        verify(orderDetailEntityMapper).toDomain(detailEntity);
    }


}
