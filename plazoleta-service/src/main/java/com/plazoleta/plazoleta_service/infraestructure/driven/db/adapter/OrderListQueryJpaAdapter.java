package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.application.port.out.OrderListQueryPersistencePort;
import com.plazoleta.plazoleta_service.domain.model.Pedido;
import com.plazoleta.plazoleta_service.domain.model.PedidoDetalle;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.OrderEntity;

import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.OrderRepository;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.DishRepository;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.OrderDetailEntityMapper;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.OrderEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderListQueryJpaAdapter implements OrderListQueryPersistencePort {
    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final OrderDetailEntityMapper orderDetailEntityMapper;

    @Override
    public Page<Pedido> getOrdersByRestaurant(Long restauranteId, String estado, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderEntity> orderEntities;
        if (estado != null && !estado.isEmpty()) {
            orderEntities = orderRepository.findByRestaurante_IdAndEstado(restauranteId, estado, pageable);
        } else {
            orderEntities = orderRepository.findByRestaurante_Id(restauranteId, pageable);
        }
        return orderEntities.map(orderEntity -> {
            Pedido pedido = orderEntityMapper.toDomain(orderEntity);
            List<PedidoDetalle> detalles = orderEntity.getDetalles().stream()
                    .map(orderDetailEntity -> {
                        PedidoDetalle detalle = orderDetailEntityMapper.toDomain(orderDetailEntity);
                        if (detalle.getPlatoId() == null) {
                            throw new IllegalArgumentException("Dish ID in order detail is null for pedidoId: " + orderEntity.getId());
                        }
                        if (detalle.getId() == null) {
                            throw new IllegalArgumentException("Order detail ID is null for pedidoId: " + orderEntity.getId());
                        }
                        return detalle;
                    })
                    .toList();
            pedido.setDetalles(detalles);
            return pedido;
        });
    }
}
