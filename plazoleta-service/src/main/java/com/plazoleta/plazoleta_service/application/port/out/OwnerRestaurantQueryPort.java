package com.plazoleta.plazoleta_service.application.port.out;

public interface OwnerRestaurantQueryPort {
    boolean isOwnerOfRestaurant(Long ownerId, Long restauranteId);
}


