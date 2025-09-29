package com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper;

import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.DishEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantEntity;
import org.springframework.stereotype.Component;

@Component
public class DishEntityMapper {
    public DishEntity toEntity(Plato plato, RestaurantEntity restaurantEntity) {
        DishEntity entity = new DishEntity();
        entity.setId(plato.getId());
        entity.setNombre(plato.getNombre());
        entity.setPrecio(plato.getPrecio());
        entity.setDescripcion(plato.getDescripcion());
        entity.setUrlImagen(plato.getUrlImagen());
        entity.setCategoria(plato.getCategoria());
        entity.setActivo(plato.getActivo());
        entity.setRestaurante(restaurantEntity);
        return entity;
    }

    public Plato toDomain(DishEntity entity) {
        Plato plato = new Plato();
        plato.setId(entity.getId());
        plato.setNombre(entity.getNombre());
        plato.setPrecio(entity.getPrecio());
        plato.setDescripcion(entity.getDescripcion());
        plato.setUrlImagen(entity.getUrlImagen());
        plato.setCategoria(entity.getCategoria());
        plato.setActivo(entity.getActivo());
        plato.setRestauranteId(entity.getRestaurante() != null ? entity.getRestaurante().getId() : null);
        return plato;
    }
}

