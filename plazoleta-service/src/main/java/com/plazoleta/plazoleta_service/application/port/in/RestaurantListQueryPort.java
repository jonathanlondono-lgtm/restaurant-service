package com.plazoleta.plazoleta_service.application.port.in;

import com.plazoleta.plazoleta_service.application.dto.RestaurantListResponseDto;
import java.util.List;

public interface RestaurantListQueryPort {
    List<RestaurantListResponseDto> getAllRestaurants();
}


