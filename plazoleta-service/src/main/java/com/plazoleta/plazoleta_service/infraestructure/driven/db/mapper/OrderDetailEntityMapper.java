package com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper;

import com.plazoleta.plazoleta_service.domain.model.PedidoDetalle;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.DishEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.OrderDetailEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailEntityMapper {

    public OrderDetailEntity toEntity(PedidoDetalle detalle) {
        if (detalle == null) {
            return null;
        }

        OrderDetailEntity entity = new OrderDetailEntity();
        entity.setId(detalle.getId());
        entity.setCantidad(detalle.getCantidad());


        if (detalle.getPedidoId() != null) {
            var pedido = new OrderEntity();
            pedido.setId(detalle.getPedidoId());
            entity.setPedido(pedido);
        }

        if (detalle.getPlatoId() != null) {
            var plato = new DishEntity();
            plato.setId(detalle.getPlatoId());
            entity.setPlato(plato);
        }

        return entity;
    }

    public PedidoDetalle toDomain(OrderDetailEntity entity) {
        if (entity == null) {
            return null;
        }

        return new PedidoDetalle(
                entity.getId(),
                entity.getPedido() != null ? entity.getPedido().getId() : null,
                entity.getPlato() != null ? entity.getPlato().getId() : null,
                entity.getCantidad()
        );
    }
}

