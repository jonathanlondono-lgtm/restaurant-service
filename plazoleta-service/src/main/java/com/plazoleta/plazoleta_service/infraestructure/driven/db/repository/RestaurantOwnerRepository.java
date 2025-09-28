package com.plazoleta.plazoleta_service.infraestructure.driven.db.repository;

import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantOwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantOwnerRepository extends JpaRepository<RestaurantOwnerEntity, Long> {
}