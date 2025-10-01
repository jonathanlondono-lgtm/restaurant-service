package com.plazoleta.plazoleta_service.application.port.out;

public interface EventPublisherPort {
    void publishOrderCreatedEvent(Long orderId, Long clientId, Long restaurantId, String status, String date);
}


