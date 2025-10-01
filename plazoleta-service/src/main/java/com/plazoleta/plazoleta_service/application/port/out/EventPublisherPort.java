package com.plazoleta.plazoleta_service.application.port.out;


import com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter.dto.OrderEventDto;

public interface EventPublisherPort {
    void publishOrderEvent(OrderEventDto eventDto);
}
