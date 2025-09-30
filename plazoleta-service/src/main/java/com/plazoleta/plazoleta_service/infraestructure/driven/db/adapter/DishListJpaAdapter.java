package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.application.port.out.DishListQueryPersistencePort;
import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.DishEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.DishEntityMapper;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DishListJpaAdapter implements DishListQueryPersistencePort {
    private final DishRepository dishRepository;
    private final DishEntityMapper dishEntityMapper;

    @Override
    public Page<Plato> getDishesByRestaurant(Long restauranteId, String category, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DishEntity> dishEntities;
        if (category != null && !category.isEmpty()) {
            dishEntities = dishRepository.findByRestauranteIdAndCategoria(restauranteId, category, pageable);
        } else {
            dishEntities = dishRepository.findByRestauranteId(restauranteId, pageable);
        }
        List<Plato> platos = dishEntities.getContent().stream()
                .map(dishEntityMapper::toDomain)
                .collect(Collectors.toList());
        return new PageImpl<>(platos, pageable, dishEntities.getTotalElements());
    }
}

