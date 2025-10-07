package com.plazoleta.plazoleta_service.infraestructure.driven.db.repository;

import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    boolean existsByClienteIdAndEstadoIn(Long clienteId, List<String> estados);
    Page<OrderEntity> findByRestaurante_Id(Long restauranteId, Pageable pageable);
    Page<OrderEntity> findByRestaurante_IdAndEstado(Long restauranteId, String estado, Pageable pageable);
    OrderEntity findByIdAndRestaurante_IdAndEstado(Long id, Long restauranteId, String estado);
    Optional<OrderEntity> findByClienteIdAndEstado(Long clienteId, String estado);
    Optional<OrderEntity> findByClienteIdAndEstadoIn(Long clienteId, List<String> estados);
}