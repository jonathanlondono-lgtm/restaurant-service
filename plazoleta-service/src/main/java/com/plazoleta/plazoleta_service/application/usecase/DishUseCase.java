package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.port.in.DishServicePort;
import com.plazoleta.plazoleta_service.application.port.out.DishPersistencePort;
import com.plazoleta.plazoleta_service.application.port.out.OwnerRestaurantQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.TokenServicePort;
import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.application.dto.DishCreateRequestDto;
import com.plazoleta.plazoleta_service.domain.service.RegexValidationService;
import com.plazoleta.plazoleta_service.domain.exception.InvalidDishFieldException;
import com.plazoleta.plazoleta_service.domain.exception.InvalidDishPriceException;
import com.plazoleta.plazoleta_service.domain.exception.OwnerNotAuthorizedException;
import com.plazoleta.plazoleta_service.domain.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishUseCase implements DishServicePort {
    private final DishPersistencePort dishPersistencePort;
    private final OwnerRestaurantQueryPort ownerRestaurantQueryPort;
    private final TokenServicePort tokenServicePort;
    private final RegexValidationService regexValidationService;

    @Override
    public Plato createDish(DishCreateRequestDto dto, String bearerToken) {
        if (dto.getNombre() == null || dto.getNombre().isBlank())
            throw new InvalidDishFieldException(ExceptionMessages.INVALID_DISH_NAME);
        if (dto.getDescripcion() == null || dto.getDescripcion().isBlank())
            throw new InvalidDishFieldException(ExceptionMessages.INVALID_DISH_DESCRIPTION);
        if (dto.getUrlImagen() == null || dto.getUrlImagen().isBlank())
            throw new InvalidDishFieldException(ExceptionMessages.INVALID_DISH_URL);
        if (dto.getCategoria() == null || dto.getCategoria().isBlank())
            throw new InvalidDishFieldException(ExceptionMessages.INVALID_DISH_CATEGORY);
        if (dto.getRestauranteId() == null)
            throw new InvalidDishFieldException(ExceptionMessages.INVALID_DISH_RESTAURANT_ID);
        if (dto.getPrecio() == null || !regexValidationService.isPositiveInteger(dto.getPrecio()))
            throw new InvalidDishPriceException(ExceptionMessages.INVALID_DISH_PRICE);

        Long ownerId = tokenServicePort.extractUserId(bearerToken);
        if (!ownerRestaurantQueryPort.isOwnerOfRestaurant(ownerId, dto.getRestauranteId())) {
            throw new OwnerNotAuthorizedException(ExceptionMessages.NOT_ACCES);
        }

        Plato plato = new Plato();
        plato.setNombre(dto.getNombre());
        plato.setPrecio(new java.math.BigDecimal(dto.getPrecio()));
        plato.setDescripcion(dto.getDescripcion());
        plato.setUrlImagen(dto.getUrlImagen());
        plato.setCategoria(dto.getCategoria());
        plato.setRestauranteId(dto.getRestauranteId());
        plato.setActivo(true);

        return dishPersistencePort.saveDish(dto.getRestauranteId(), plato);
    }
}
