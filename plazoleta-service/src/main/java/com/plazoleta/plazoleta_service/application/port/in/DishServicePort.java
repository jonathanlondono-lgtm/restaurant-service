package com.plazoleta.plazoleta_service.application.port.in;

import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.application.dto.DishCreateRequestDto;

public interface DishServicePort {
    Plato createDish(DishCreateRequestDto dto, String bearerToken);
}

