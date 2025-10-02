package com.plazoleta.plazoleta_service.application.port.in;

import com.plazoleta.plazoleta_service.application.dto.DishPageResponseDto;

public interface DishListQueryPort {
    DishPageResponseDto getDishesByRestaurant(Long restauranteId, String category, int page, int size);
}

