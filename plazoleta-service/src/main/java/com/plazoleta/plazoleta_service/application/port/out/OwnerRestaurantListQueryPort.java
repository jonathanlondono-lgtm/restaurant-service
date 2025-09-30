package com.plazoleta.plazoleta_service.application.port.out;

import com.plazoleta.plazoleta_service.domain.model.Restaurante;

import java.util.List;

public interface OwnerRestaurantListQueryPort {
    List<Restaurante> findRestaurantsByOwnerId(Long ownerId);

}
