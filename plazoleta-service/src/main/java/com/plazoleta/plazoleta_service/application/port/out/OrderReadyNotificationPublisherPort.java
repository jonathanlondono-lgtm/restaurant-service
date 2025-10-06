package com.plazoleta.plazoleta_service.application.port.out;

import com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter.dto.OrderReadyNotificationEventDto;

public interface OrderReadyNotificationPublisherPort {
    void publishOrderReadyNotification(OrderReadyNotificationEventDto eventDto);
}
