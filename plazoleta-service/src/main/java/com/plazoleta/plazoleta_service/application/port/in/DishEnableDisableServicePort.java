package com.plazoleta.plazoleta_service.application.port.in;

import com.plazoleta.plazoleta_service.application.dto.DishEnableDisableRequestDto;

public interface DishEnableDisableServicePort {
    void enableDisableDish(DishEnableDisableRequestDto request, String bearerToken);
}

