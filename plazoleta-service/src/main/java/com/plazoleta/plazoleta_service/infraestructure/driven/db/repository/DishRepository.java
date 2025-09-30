package com.plazoleta.plazoleta_service.infraestructure.driven.db.repository;

import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<DishEntity, Long> {
    Page<DishEntity> findByRestauranteId(Long restauranteId, Pageable pageable);
    Page<DishEntity> findByRestauranteIdAndCategoria(Long restauranteId, String categoria, Pageable pageable);
}
