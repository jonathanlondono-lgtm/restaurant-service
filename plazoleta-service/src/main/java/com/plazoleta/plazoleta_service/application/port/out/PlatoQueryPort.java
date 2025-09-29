package com.plazoleta.plazoleta_service.application.port.out;

import com.plazoleta.plazoleta_service.domain.model.Plato;

public interface PlatoQueryPort {

    Plato findPlatoByIdAndRestauranteId(Long platoId, Long restauranteId);
}