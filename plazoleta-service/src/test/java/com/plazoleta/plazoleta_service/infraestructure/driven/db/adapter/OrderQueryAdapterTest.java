package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderQueryAdapterTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderQueryAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void hasActiveOrder_true() {
        Long clientId = 1L;
        List<String> estados = List.of("PENDIENTE", "EN_PREPARACION", "LISTO");
        when(orderRepository.existsByClienteIdAndEstadoIn(clientId, estados)).thenReturn(true);
        assertTrue(adapter.hasActiveOrder(clientId));
    }

    @Test
    void hasActiveOrder_false() {
        Long clientId = 2L;
        List<String> estados = List.of("PENDIENTE", "EN_PREPARACION", "LISTO");
        when(orderRepository.existsByClienteIdAndEstadoIn(clientId, estados)).thenReturn(false);
        assertFalse(adapter.hasActiveOrder(clientId));
    }
}

