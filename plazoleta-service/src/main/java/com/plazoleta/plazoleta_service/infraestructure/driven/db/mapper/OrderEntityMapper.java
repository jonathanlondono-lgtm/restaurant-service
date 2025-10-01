package com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper;

import com.plazoleta.plazoleta_service.domain.model.Pedido;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.OrderEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface OrderEntityMapper {
    @Mapping(target = "restaurante", source = "restauranteId", qualifiedByName = "mapRestauranteId")
    OrderEntity toEntity(Pedido pedido);

    Pedido toDomain(OrderEntity entity);

    @Named("mapRestauranteId")
    default RestaurantEntity mapRestauranteId(Long restauranteId) {
        if (restauranteId == null) return null;
        RestaurantEntity entity = new RestaurantEntity();
        entity.setId(restauranteId);
        return entity;
    }
}
