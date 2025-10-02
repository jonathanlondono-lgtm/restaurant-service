package com.plazoleta.plazoleta_service.application.port.in;

import com.plazoleta.plazoleta_service.application.dto.OrderCreateRequestDto;

public interface OrderCreateUseCasePort {
    void createOrder(OrderCreateRequestDto orderCreateRequestDto, String bearerToken);
}

