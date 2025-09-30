package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.RestaurantCreateRequestDto;
import com.plazoleta.plazoleta_service.application.port.in.RestaurantServicePort;
import com.plazoleta.plazoleta_service.application.port.out.RestaurantOwnerPersistencePort;
import com.plazoleta.plazoleta_service.application.port.out.RestaurantPersistencePort;
import com.plazoleta.plazoleta_service.application.port.out.UserClientPort;
import com.plazoleta.plazoleta_service.domain.exception.InvalidNitException;
import com.plazoleta.plazoleta_service.domain.exception.InvalidPhoneException;
import com.plazoleta.plazoleta_service.domain.exception.InvalidRestaurantNameException;
import com.plazoleta.plazoleta_service.domain.exception.OwnerNotAuthorizedException;
import com.plazoleta.plazoleta_service.domain.exception.OwnerNotFoundException;
import com.plazoleta.plazoleta_service.domain.model.Restaurante;
import com.plazoleta.plazoleta_service.domain.model.RestauranteOwner;
import com.plazoleta.plazoleta_service.domain.service.NitValidationService;
import com.plazoleta.plazoleta_service.domain.service.PhoneValidationService;
import com.plazoleta.plazoleta_service.domain.service.RestaurantNameValidationService;
import com.plazoleta.plazoleta_service.domain.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantUseCase implements RestaurantServicePort {
    private final RestaurantPersistencePort restaurantPersistencePort;
    private final RestaurantOwnerPersistencePort restaurantOwnerPersistencePort;
    private final UserClientPort userClientPort;
    private final NitValidationService nitValidationService;
    private final PhoneValidationService phoneValidationService;
    private final RestaurantNameValidationService restaurantNameValidationService;

    @Override
    public Restaurante createRestaurant(RestaurantCreateRequestDto request, String bearerToken) {
        if (request.getName() == null || request.getNit() == null || request.getAddress() == null ||
                request.getPhone() == null || request.getUrlLogo() == null || request.getOwnerId() == null) {
            throw new IllegalArgumentException("All fields are required.");
        }
        if (!nitValidationService.isValid(request.getNit())) {
            throw new InvalidNitException(ExceptionMessages.INVALID_NIT);
        }
        if (!phoneValidationService.isValid(request.getPhone())) {
            throw new InvalidPhoneException(ExceptionMessages.INVALID_PHONE);
        }
        if (!restaurantNameValidationService.isValid(request.getName())) {
            throw new InvalidRestaurantNameException(ExceptionMessages.INVALID_RESTAURANT_NAME);
        }
        if (restaurantPersistencePort.existsByNit(request.getNit())) {
            throw new IllegalArgumentException("A restaurant with this NIT already exists.");
        }

        List<String> roles = userClientPort.getUserRoleById(request.getOwnerId(), bearerToken);
        if (!roles.contains("PROPIETARIO")) {
            throw new OwnerNotFoundException("Owner user does not exist or does not have OWNER role.");
        }

        Restaurante restaurante = new Restaurante();
        restaurante.setNombre(request.getName());
        restaurante.setNit(request.getNit());
        restaurante.setDireccion(request.getAddress());
        restaurante.setTelefono(request.getPhone());
        restaurante.setUrlLogo(request.getUrlLogo());
        restaurante.setFechaCreacion(LocalDateTime.now());
        restaurante.setFechaActualizacion(LocalDateTime.now());
        Restaurante saved = restaurantPersistencePort.save(restaurante);

        RestauranteOwner owner = new RestauranteOwner();
        owner.setRestauranteId(saved.getId());
        owner.setOwnerId(request.getOwnerId());
        owner.setRol("OWNER");
        owner.setFechaAsignacion(LocalDateTime.now());
        restaurantOwnerPersistencePort.save(owner);
        return saved;
    }
}
