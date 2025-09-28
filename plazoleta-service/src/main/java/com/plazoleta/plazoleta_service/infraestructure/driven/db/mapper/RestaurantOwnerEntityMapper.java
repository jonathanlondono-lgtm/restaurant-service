package com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper;

import com.plazoleta.plazoleta_service.domain.model.RestauranteOwner;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantOwnerEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantEntity;
import org.springframework.stereotype.Component;

@Component
public class RestaurantOwnerEntityMapper {
    public RestaurantOwnerEntity toEntity(RestauranteOwner owner, RestaurantEntity restaurantEntity) {
        RestaurantOwnerEntity entity = new RestaurantOwnerEntity();
        entity.setId(owner.getId());
        entity.setRestaurante(restaurantEntity);
        entity.setOwnerId(owner.getOwnerId());
        entity.setRol(owner.getRol());
        entity.setFechaAsignacion(owner.getFechaAsignacion());
        return entity;
    }

    public RestauranteOwner toDomain(RestaurantOwnerEntity entity) {
        RestauranteOwner owner = new RestauranteOwner();
        owner.setId(entity.getId());
        owner.setRestauranteId(entity.getRestaurante().getId());
        owner.setOwnerId(entity.getOwnerId());
        owner.setRol(entity.getRol());
        owner.setFechaAsignacion(entity.getFechaAsignacion());
        return owner;
    }
}

