package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.application.port.out.RestaurantOwnerPersistencePort;
import com.plazoleta.plazoleta_service.domain.model.RestauranteOwner;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantOwnerEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.RestaurantOwnerEntityMapper;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.RestaurantOwnerRepository;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantOwnerJpaAdapter implements RestaurantOwnerPersistencePort {
    private final RestaurantOwnerRepository restaurantOwnerRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantOwnerEntityMapper restaurantOwnerEntityMapper;

    @Override
    public RestauranteOwner save(RestauranteOwner restauranteOwner) {
        RestaurantEntity restaurantEntity = restaurantRepository.findById(restauranteOwner.getRestauranteId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found for owner relation"));
        RestaurantOwnerEntity entity = restaurantOwnerEntityMapper.toEntity(restauranteOwner, restaurantEntity);
        RestaurantOwnerEntity saved = restaurantOwnerRepository.save(entity);
        return restaurantOwnerEntityMapper.toDomain(saved);
    }
}


