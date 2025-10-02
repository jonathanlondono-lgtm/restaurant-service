package com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper;

import com.plazoleta.plazoleta_service.domain.model.PedidoDetalle;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.OrderDetailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDetailEntityMapper {
    OrderDetailEntity toEntity(PedidoDetalle detalle);

    @Mapping(target = "platoId", source = "plato.id")
    @Mapping(target = "pedidoId", source = "pedido.id")
    PedidoDetalle toDomain(OrderDetailEntity entity);
}
