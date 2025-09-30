package com.plazoleta.plazoleta_service.infraestructure.driven.db.repository;

import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantOwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwnerEntity, Long> {
    boolean existsByOwnerIdAndRestaurante_Id(Long ownerId, Long restauranteId);

    List<RestaurantOwnerEntity> findByOwnerId(Long ownerId);
}