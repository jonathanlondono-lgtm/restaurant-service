package com.plazoleta.plazoleta_service.infraestructure.driven.db.repository;

import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    Optional<RestaurantEntity> findByNit(String nit);
    boolean existsByNit(String nit);
}