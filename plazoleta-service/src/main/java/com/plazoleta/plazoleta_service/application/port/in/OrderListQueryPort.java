package com.plazoleta.plazoleta_service.application.port.in;

import com.plazoleta.plazoleta_service.application.dto.OrderPageResponseDto;

public interface OrderListQueryPort {
    OrderPageResponseDto getOrdersByRestaurant(String bearerToken, String estado, int page, int size);
}
