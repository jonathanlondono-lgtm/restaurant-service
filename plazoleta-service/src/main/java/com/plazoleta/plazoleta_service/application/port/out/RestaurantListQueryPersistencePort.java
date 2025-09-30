package com.plazoleta.plazoleta_service.application.port.out;

import com.plazoleta.plazoleta_service.domain.model.Restaurante;
import java.util.List;

public interface RestaurantListQueryPersistencePort {
    List<Restaurante> getAllRestaurants();
}

