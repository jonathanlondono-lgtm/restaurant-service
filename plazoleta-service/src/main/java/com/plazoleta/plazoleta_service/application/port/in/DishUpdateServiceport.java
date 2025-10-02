package com.plazoleta.plazoleta_service.application.port.in;

import com.plazoleta.plazoleta_service.application.dto.DishUpdateRequestDto;
import com.plazoleta.plazoleta_service.application.dto.DishUpdateResponseDto;

public interface DishUpdateServiceport {
    DishUpdateResponseDto updateDish(DishUpdateRequestDto request, String bearerToken);

}
