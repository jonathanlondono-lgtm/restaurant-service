package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.application.port.out.OwnerRestaurantListQueryPort;
import com.plazoleta.plazoleta_service.domain.model.Restaurante;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantOwnerEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.RestaurantEntityMapper;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.RestaurantOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OwnerRestaurantListJpaAdapter implements OwnerRestaurantListQueryPort {
    private final RestaurantOwnerRepository restaurantOwnerRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public List<Restaurante> findRestaurantsByOwnerId(Long ownerId) {
        List<RestaurantOwnerEntity> ownerEntities = restaurantOwnerRepository.findByOwnerId(ownerId);
        return ownerEntities.stream()
                .map(ownerEntity -> restaurantEntityMapper.toDomain(ownerEntity.getRestaurante()))
                .collect(Collectors.toList());
    }
}

