package com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper;

import com.plazoleta.plazoleta_service.domain.model.PedidoDetalle;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.OrderDetailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderDetailEntityMapper {
    OrderDetailEntity toEntity(PedidoDetalle detalle);
    PedidoDetalle toDomain(OrderDetailEntity entity);
}


