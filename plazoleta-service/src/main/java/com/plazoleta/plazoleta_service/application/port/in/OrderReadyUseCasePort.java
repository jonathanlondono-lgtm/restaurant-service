package com.plazoleta.plazoleta_service.application.port.in;

import com.plazoleta.plazoleta_service.application.dto.OrderAssignRequestDto;

public interface OrderReadyUseCasePort {
    void markOrderAsReady(OrderAssignRequestDto requestDto, String bearerToken);
}
