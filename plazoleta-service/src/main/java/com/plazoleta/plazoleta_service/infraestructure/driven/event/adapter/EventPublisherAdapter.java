package com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter;

import com.plazoleta.plazoleta_service.application.port.out.EventPublisherPort;
import com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter.config.RabbitMQConfig;
import com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter.dto.OrderEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisherAdapter implements EventPublisherPort {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishOrderEvent(OrderEventDto eventDto) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.PEDIDO_CREADO_QUEUE, eventDto);
    }
}
