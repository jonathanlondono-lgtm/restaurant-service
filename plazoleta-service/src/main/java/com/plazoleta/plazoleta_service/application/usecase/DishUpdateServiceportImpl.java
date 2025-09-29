package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.port.in.DishUpdateServiceport;
import com.plazoleta.plazoleta_service.application.port.out.OwnerRestaurantQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.PlatoCommandPort;
import com.plazoleta.plazoleta_service.application.port.out.PlatoQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.TokenServicePort;
import com.plazoleta.plazoleta_service.domain.exception.InvalidDishPriceException;
import com.plazoleta.plazoleta_service.domain.exception.OwnerNotAuthorizedException;
import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.domain.util.ExceptionMessages;
import com.plazoleta.plazoleta_service.domain.util.RegexPatterns;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.DishUpdateRequestDto;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.DishUpdateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishUpdateServiceportImpl implements DishUpdateServiceport {
    private final TokenServicePort tokenServicePort;
    private final OwnerRestaurantQueryPort ownerRestaurantQueryPort;
    private final PlatoQueryPort platoQueryPort;
    private final PlatoCommandPort platoCommandPort;

    @Override
    public DishUpdateResponseDto updateDish(DishUpdateRequestDto request, String bearerToken) {
        if (request.getPrecio() == null || !request.getPrecio().toPlainString().matches(RegexPatterns.POSITIVE_INTEGER)) {
            throw new InvalidDishPriceException(ExceptionMessages.INVALID_DISH_PRICE);
        }
        if (request.getDescripcion() == null || request.getDescripcion().isBlank()) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_DISH_DESCRIPTION);
        }
        if (request.getPlatoId() == null || request.getRestauranteId() == null) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_DISH_RESTAURANT_ID);
        }

        Long userId = tokenServicePort.extractUserId(bearerToken);

        if (!ownerRestaurantQueryPort.isOwnerOfRestaurant(userId, request.getRestauranteId())) {
            throw new OwnerNotAuthorizedException(ExceptionMessages.NOT_ACCES);
        }
        // Verificar que el plato existe y pertenece al restaurante
        Plato plato = platoQueryPort.findPlatoByIdAndRestauranteId(request.getPlatoId(), request.getRestauranteId());
        if (plato == null) {
            throw new IllegalArgumentException("Dish not found for this restaurant.");
        }
        // Actualizar los campos permitidos
        plato.setPrecio(request.getPrecio());
        plato.setDescripcion(request.getDescripcion());

        platoCommandPort.updatePlato(plato);

        // Mapear a DTO de salida
        return new DishUpdateResponseDto(
            plato.getId(),
            plato.getNombre(),
            plato.getPrecio(),
            plato.getDescripcion(),
            plato.getUrlImagen(),
            plato.getCategoria(),
            plato.getActivo(),
            plato.getRestauranteId()
        );
    }
}
