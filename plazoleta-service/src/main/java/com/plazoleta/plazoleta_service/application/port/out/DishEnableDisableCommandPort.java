package com.plazoleta.plazoleta_service.application.port.out;

import com.plazoleta.plazoleta_service.domain.model.Plato;

public interface DishEnableDisableCommandPort {
    void updateDishEnabledStatus(Plato plato, boolean enabled);
}