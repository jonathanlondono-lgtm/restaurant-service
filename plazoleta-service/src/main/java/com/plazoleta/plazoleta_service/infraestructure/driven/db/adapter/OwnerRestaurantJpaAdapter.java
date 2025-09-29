package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.application.port.out.OwnerRestaurantQueryPort;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.RestaurantOwnerEntityMapper;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.RestaurantOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OwnerRestaurantJpaAdapter implements OwnerRestaurantQueryPort {
    private final RestaurantOwnerRepository restaurantOwnerRepository;
    private final RestaurantOwnerEntityMapper restaurantOwnerEntityMapper;

    @Override
    public boolean isOwnerOfRestaurant(Long ownerId, Long restauranteId) {
        return restaurantOwnerRepository.existsByOwnerIdAndRestaurante_Id(ownerId, restauranteId);
    }
}
