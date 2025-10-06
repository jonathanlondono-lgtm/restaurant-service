package com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper;

import com.plazoleta.plazoleta_service.domain.model.Pedido;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.OrderEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderEntityMapper {

    public OrderEntity toEntity(Pedido pedido) {
        if (pedido == null) return null;

        OrderEntity entity = new OrderEntity();
        entity.setId(pedido.getId());
        entity.setClienteId(pedido.getClienteId());
        entity.setEstado(pedido.getEstado());
        entity.setEmpleadoAsignadoId(pedido.getEmpleadoAsignadoId());
        entity.setFechaCreacion(pedido.getFechaCreacion());
        entity.setFechaActualizacion(pedido.getFechaActualizacion());
        entity.setPinSeguridad(pedido.getPinSeguridad());
        entity.setPhone(pedido.getPhone());

        if (pedido.getRestauranteId() != null) {
            RestaurantEntity restaurante = new RestaurantEntity();
            restaurante.setId(pedido.getRestauranteId());
            entity.setRestaurante(restaurante);
        }

        return entity;
    }

    public Pedido toDomain(OrderEntity entity) {
        if (entity == null) return null;

        Pedido pedido = new Pedido();
        pedido.setId(entity.getId());
        pedido.setClienteId(entity.getClienteId());
        pedido.setEstado(entity.getEstado());
        pedido.setEmpleadoAsignadoId(entity.getEmpleadoAsignadoId());
        pedido.setFechaCreacion(entity.getFechaCreacion());
        pedido.setFechaActualizacion(entity.getFechaActualizacion());
        pedido.setPinSeguridad(entity.getPinSeguridad());
        pedido.setPhone(entity.getPhone());

        if (entity.getRestaurante() != null) {
            pedido.setRestauranteId(entity.getRestaurante().getId());
        }

        return pedido;
    }
}
