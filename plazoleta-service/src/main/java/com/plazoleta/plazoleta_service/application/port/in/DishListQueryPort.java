package com.plazoleta.plazoleta_service.application.port.in;

import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.DishPageResponseDto;

public interface DishListQueryPort {
    DishPageResponseDto getDishesByRestaurant(Long restauranteId, String category, int page, int size);
}

