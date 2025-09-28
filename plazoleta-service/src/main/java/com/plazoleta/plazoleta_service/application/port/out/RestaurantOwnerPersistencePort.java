package com.plazoleta.plazoleta_service.application.port.out;

import com.plazoleta.plazoleta_service.domain.model.RestauranteOwner;

public interface RestaurantOwnerPersistencePort {
    RestauranteOwner save(RestauranteOwner restauranteOwner);
}

