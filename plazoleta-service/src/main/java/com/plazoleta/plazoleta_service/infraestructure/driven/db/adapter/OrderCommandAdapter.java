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
        // Mapear pedido a entidad
        OrderEntity orderEntity = orderEntityMapper.toEntity(pedido);

        // Crear los detalles y asociarlos al pedido
        List<OrderDetailEntity> detailEntities = pedido.getDetalles().stream()
                .map(detalle -> {
                    OrderDetailEntity entity = new OrderDetailEntity();
                    entity.setPedido(orderEntity); // aquí se enlaza con el pedido en memoria
                    DishEntity dishEntity = new DishEntity();
                    dishEntity.setId(detalle.getPlatoId());
                    entity.setPlato(dishEntity);
                    entity.setCantidad(detalle.getCantidad());
                    return entity;
                })
                .toList();

        orderEntity.setDetalles(detailEntities);

        // Guardar en cascada (INSERT pedido y luego detalles)
        OrderEntity savedOrder = orderRepository.save(orderEntity);

        // Reflejar el id en el dominio
        pedido.setId(savedOrder.getId());
        return pedido;
    }

    @Override
    public Pedido updateOrder(Pedido pedido) {
        // Buscar el pedido existente
        OrderEntity existingOrder = orderRepository.findById(pedido.getId()).orElse(null);
        if (existingOrder == null) {
            return null;
        }
        // Actualizar los campos necesarios
        existingOrder.setEmpleadoAsignadoId(pedido.getEmpleadoAsignadoId());
        existingOrder.setEstado(pedido.getEstado());
        existingOrder.setFechaActualizacion(pedido.getFechaActualizacion());
        // Guardar el pedido actualizado
        OrderEntity updatedOrder = orderRepository.save(existingOrder);
        // Mapear y devolver
        return orderEntityMapper.toDomain(updatedOrder);
    }
}
