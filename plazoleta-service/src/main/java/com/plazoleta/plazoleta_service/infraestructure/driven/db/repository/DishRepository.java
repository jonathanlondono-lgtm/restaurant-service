package com.plazoleta.plazoleta_service.infraestructure.driven.db.repository;

import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<DishEntity, Long> {
}

