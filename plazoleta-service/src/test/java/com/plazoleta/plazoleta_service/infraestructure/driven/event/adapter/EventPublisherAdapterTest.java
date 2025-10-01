package com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter;

import com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter.config.RabbitMQConfig;
import com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter.dto.OrderEventDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.*;

class EventPublisherAdapterTest {
    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private EventPublisherAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void publishOrderEvent_success() {
        OrderEventDto eventDto = new OrderEventDto(100L, 1L, 2L, "PENDIENTE", "2025-10-01T12:00:00Z", "plazoleta-service", null, null);
        adapter.publishOrderEvent(eventDto);
        verify(rabbitTemplate).convertAndSend(RabbitMQConfig.PEDIDO_CREADO_QUEUE, eventDto);
    }
}

