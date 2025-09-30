package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.application.port.out.RestaurantListQueryPersistencePort;
import com.plazoleta.plazoleta_service.domain.model.Restaurante;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.RestaurantEntityMapper;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RestaurantListJpaAdapter implements RestaurantListQueryPersistencePort {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public List<Restaurante> getAllRestaurants() {
        List<RestaurantEntity> entities = restaurantRepository.findAll();
        return entities.stream()
                .map(restaurantEntityMapper::toDomain)
                .toList();
    }
}
