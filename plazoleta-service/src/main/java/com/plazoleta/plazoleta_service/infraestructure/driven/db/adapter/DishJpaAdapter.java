package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.application.port.out.DishPersistencePort;
import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.DishEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.DishEntityMapper;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.DishRepository;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DishJpaAdapter implements DishPersistencePort {
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishEntityMapper dishEntityMapper;

    @Override
    public Plato saveDish(Long restauranteId, Plato plato) {
        RestaurantEntity restaurantEntity = restaurantRepository.findById(restauranteId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        DishEntity entity = dishEntityMapper.toEntity(plato, restaurantEntity);
        DishEntity saved = dishRepository.save(entity);
        return dishEntityMapper.toDomain(saved);
    }
}

