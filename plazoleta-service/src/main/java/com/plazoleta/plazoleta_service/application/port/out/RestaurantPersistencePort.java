package com.plazoleta.plazoleta_service.application.port.out;

import com.plazoleta.plazoleta_service.domain.model.Restaurante;

public interface RestaurantPersistencePort {
    Restaurante save(Restaurante restaurante);
    boolean existsByNit(String nit);
}

