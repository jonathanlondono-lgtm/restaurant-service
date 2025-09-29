package com.plazoleta.plazoleta_service.application.port.out;

import com.plazoleta.plazoleta_service.domain.model.Plato;

public interface DishPersistencePort {
    Plato saveDish(Long restauranteId, Plato plato);
}