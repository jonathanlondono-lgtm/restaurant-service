package com.plazoleta.plazoleta_service.application.port.out;

import com.plazoleta.plazoleta_service.domain.model.Plato;
import org.springframework.data.domain.Page;

public interface DishListQueryPersistencePort {
    Page<Plato> getDishesByRestaurant(Long restauranteId, String category, int page, int size);
}

