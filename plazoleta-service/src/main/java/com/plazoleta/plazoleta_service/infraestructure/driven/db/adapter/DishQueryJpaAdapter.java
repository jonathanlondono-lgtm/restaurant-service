package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.application.port.out.DishQueryPort;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.DishEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DishQueryJpaAdapter implements DishQueryPort {
    private final DishRepository dishRepository;

    @Override
    public String getDishNameByIdAndRestaurant(Long dishId, Long restaurantId) {
        return dishRepository.findById(dishId)
                .filter(dish -> dish.getRestaurante() != null && dish.getRestaurante().getId().equals(restaurantId))
                .map(DishEntity::getNombre)
                .orElse(null);
    }

    @Override
    public BigDecimal getDishPriceById(Long dishId) {
        return dishRepository.findById(dishId)
                .map(DishEntity::getPrecio)
                .orElse(null);
    }
}

