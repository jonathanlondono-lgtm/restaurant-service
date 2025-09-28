package com.plazoleta.plazoleta_service.application.port.in;

import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.RestaurantCreateRequestDto;
import com.plazoleta.plazoleta_service.domain.model.Restaurante;

public interface RestaurantServicePort {
    Restaurante createRestaurant(RestaurantCreateRequestDto request, String bearerToken);
}