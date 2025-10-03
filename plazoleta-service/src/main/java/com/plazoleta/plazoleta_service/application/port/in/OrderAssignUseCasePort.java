package com.plazoleta.plazoleta_service.application.port.in;

import com.plazoleta.plazoleta_service.application.dto.OrderAssignRequestDto;

public interface OrderAssignUseCasePort {
    void assignOrderToEmployee(OrderAssignRequestDto requestDto, String bearerToken);
}

