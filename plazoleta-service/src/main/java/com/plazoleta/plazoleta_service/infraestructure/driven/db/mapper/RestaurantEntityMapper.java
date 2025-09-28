package com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper;

import com.plazoleta.plazoleta_service.domain.model.Restaurante;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantEntity;
import org.springframework.stereotype.Component;

@Component
public class RestaurantEntityMapper {
    public RestaurantEntity toEntity(Restaurante restaurante) {
        RestaurantEntity entity = new RestaurantEntity();
        entity.setId(restaurante.getId());
        entity.setNombre(restaurante.getNombre());
        entity.setNit(restaurante.getNit());
        entity.setDireccion(restaurante.getDireccion());
        entity.setTelefono(restaurante.getTelefono());
        entity.setUrlLogo(restaurante.getUrlLogo());
        entity.setFechaCreacion(restaurante.getFechaCreacion());
        entity.setFechaActualizacion(restaurante.getFechaActualizacion());
        return entity;
    }

    public Restaurante toDomain(RestaurantEntity entity) {
        Restaurante restaurante = new Restaurante();
        restaurante.setId(entity.getId());
        restaurante.setNombre(entity.getNombre());
        restaurante.setNit(entity.getNit());
        restaurante.setDireccion(entity.getDireccion());
        restaurante.setTelefono(entity.getTelefono());
        restaurante.setUrlLogo(entity.getUrlLogo());
        restaurante.setFechaCreacion(entity.getFechaCreacion());
        restaurante.setFechaActualizacion(entity.getFechaActualizacion());
        return restaurante;
    }
}

