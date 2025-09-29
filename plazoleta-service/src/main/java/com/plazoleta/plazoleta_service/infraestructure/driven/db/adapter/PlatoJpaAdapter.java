package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.application.port.out.PlatoQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.PlatoCommandPort;
import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.DishEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.DishEntityMapper;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PlatoJpaAdapter implements PlatoQueryPort, PlatoCommandPort {
    private final DishRepository dishRepository;
    private final DishEntityMapper dishEntityMapper;

    @Override
    public Plato findPlatoByIdAndRestauranteId(Long platoId, Long restauranteId) {
        Optional<DishEntity> entityOpt = dishRepository.findById(platoId);
        if (entityOpt.isPresent()) {
            DishEntity entity = entityOpt.get();
            if (entity.getRestaurante() != null && restauranteId.equals(entity.getRestaurante().getId())) {
                return dishEntityMapper.toDomain(entity);
            }
        }
        return null;
    }

    @Override
    public void updatePlato(Plato plato) {
        Optional<DishEntity> entityOpt = dishRepository.findById(plato.getId());
        if (entityOpt.isPresent()) {
            DishEntity entity = entityOpt.get();
            entity.setPrecio(plato.getPrecio());
            entity.setDescripcion(plato.getDescripcion());
            dishRepository.save(entity);
        }
    }
}

