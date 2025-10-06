package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.application.port.out.OrderCommandPort;
import com.plazoleta.plazoleta_service.domain.model.Pedido;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.DishEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.OrderEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.OrderDetailEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.OrderEntityMapper;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderCommandAdapter implements OrderCommandPort {
    private final OrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;

    @Override
    public Pedido saveOrder(Pedido pedido) {
        OrderEntity orderEntity = orderEntityMapper.toEntity(pedido);

        List<OrderDetailEntity> detailEntities = pedido.getDetalles().stream()
                .map(detalle -> {
                    OrderDetailEntity entity = new OrderDetailEntity();
                    entity.setPedido(orderEntity);
                    DishEntity dishEntity = new DishEntity();
                    dishEntity.setId(detalle.getPlatoId());
                    entity.setPlato(dishEntity);
                    entity.setCantidad(detalle.getCantidad());
                    return entity;
                })
                .toList();

        orderEntity.setDetalles(detailEntities);

        OrderEntity savedOrder = orderRepository.save(orderEntity);

        pedido.setId(savedOrder.getId());
        return pedido;
    }

    @Override
    public Pedido updateOrder(Pedido pedido) {
        OrderEntity existingOrder = orderRepository.findById(pedido.getId()).orElse(null);
        if (existingOrder == null) {
            return null;
        }
        existingOrder.setEmpleadoAsignadoId(pedido.getEmpleadoAsignadoId());
        existingOrder.setEstado(pedido.getEstado());
        existingOrder.setFechaActualizacion(pedido.getFechaActualizacion());
        existingOrder.setPinSeguridad(pedido.getPinSeguridad());

        OrderEntity updatedOrder = orderRepository.save(existingOrder);
        return orderEntityMapper.toDomain(updatedOrder);
    }
}
