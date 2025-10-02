package com.plazoleta.plazoleta_service.application.port.out;

import java.math.BigDecimal;

public interface DishQueryPort {
    String getDishNameByIdAndRestaurant(Long dishId, Long restaurantId);
    BigDecimal getDishPriceById(Long dishId);
}

