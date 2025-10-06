package com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter;

import com.plazoleta.plazoleta_service.application.port.out.OrderReadyNotificationPublisherPort;
import com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter.config.RabbitMQConfig;
import com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter.dto.OrderReadyNotificationEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderReadyNotificationPublisherAdapter implements OrderReadyNotificationPublisherPort {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishOrderReadyNotification(OrderReadyNotificationEventDto eventDto) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.PEDIDO_LISTO_QUEUE, eventDto);
    }
}

