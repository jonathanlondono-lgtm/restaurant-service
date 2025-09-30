package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.application.port.out.DishEnableDisableCommandPort;
import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.DishEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DishEnableDisableJpaAdapter implements DishEnableDisableCommandPort {
    private final DishRepository dishRepository;

    @Override
    public void updateDishEnabledStatus(Plato plato, boolean enabled) {
        Optional<DishEntity> entityOpt = dishRepository.findById(plato.getId());
        if (entityOpt.isPresent()) {
            DishEntity entity = entityOpt.get();
            entity.setActivo(enabled);
            dishRepository.save(entity);
        }
    }
}

