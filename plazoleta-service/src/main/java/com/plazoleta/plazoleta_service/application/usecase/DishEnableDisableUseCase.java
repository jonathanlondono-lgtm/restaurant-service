package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.port.in.DishEnableDisableServicePort;
import com.plazoleta.plazoleta_service.application.port.out.DishEnableDisableCommandPort;
import com.plazoleta.plazoleta_service.application.port.out.OwnerRestaurantQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.PlatoQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.TokenServicePort;
import com.plazoleta.plazoleta_service.domain.exception.InvalidDishFieldException;
import com.plazoleta.plazoleta_service.domain.exception.InvalidDishRestaurantIdException;
import com.plazoleta.plazoleta_service.domain.exception.OwnerNotAuthorizedException;
import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.domain.util.ExceptionMessages;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.DishEnableDisableRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishEnableDisableUseCase implements DishEnableDisableServicePort {
    private final TokenServicePort tokenServicePort;
    private final OwnerRestaurantQueryPort ownerRestaurantQueryPort;
    private final PlatoQueryPort platoQueryPort;
    private final DishEnableDisableCommandPort dishEnableDisableCommandPort;

    @Override
    public void enableDisableDish(DishEnableDisableRequestDto request, String bearerToken) {
        if (request.getPlatoId() == null) {
            throw new InvalidDishFieldException(ExceptionMessages.INVALID_DISH_FIELD);
        }
        if (request.getRestauranteId() == null) {
            throw new InvalidDishRestaurantIdException(ExceptionMessages.INVALID_DISH_RESTAURANT_ID);
        }
        if (request.getEnabled() == null) {
            throw new InvalidDishFieldException(ExceptionMessages.INVALID_DISH_FIELD);
        }
        Long userId = tokenServicePort.extractUserId(bearerToken);
        if (!ownerRestaurantQueryPort.isOwnerOfRestaurant(userId, request.getRestauranteId())) {
            throw new OwnerNotAuthorizedException(ExceptionMessages.NOT_ACCES);
        }
        Plato plato = platoQueryPort.findPlatoByIdAndRestauranteId(request.getPlatoId(), request.getRestauranteId());
        if (plato == null) {
            throw new com.plazoleta.plazoleta_service.domain.exception.DishNotFoundForRestaurantException(ExceptionMessages.DISH_NOT_FOUND_FOR_RESTAURANT);
        }
        dishEnableDisableCommandPort.updateDishEnabledStatus(plato, request.getEnabled());
    }
}
