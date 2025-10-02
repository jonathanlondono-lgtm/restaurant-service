package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.port.out.RestaurantOwnerPersistencePort;
import com.plazoleta.plazoleta_service.application.port.out.RestaurantPersistencePort;
import com.plazoleta.plazoleta_service.application.port.out.UserClientPort;
import com.plazoleta.plazoleta_service.domain.exception.InvalidNitException;
import com.plazoleta.plazoleta_service.domain.exception.InvalidPhoneException;
import com.plazoleta.plazoleta_service.domain.exception.InvalidRestaurantNameException;
import com.plazoleta.plazoleta_service.domain.exception.OwnerNotFoundException;
import com.plazoleta.plazoleta_service.domain.model.Restaurante;
import com.plazoleta.plazoleta_service.domain.model.RestauranteOwner;
import com.plazoleta.plazoleta_service.domain.service.NitValidationService;
import com.plazoleta.plazoleta_service.domain.service.PhoneValidationService;
import com.plazoleta.plazoleta_service.domain.service.RestaurantNameValidationService;
import com.plazoleta.plazoleta_service.application.dto.RestaurantCreateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {
    @Mock
    private RestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private RestaurantOwnerPersistencePort restaurantOwnerPersistencePort;
    @Mock
    private UserClientPort userClientPort;
    @Mock
    private NitValidationService nitValidationService;
    @Mock
    private PhoneValidationService phoneValidationService;
    @Mock
    private RestaurantNameValidationService restaurantNameValidationService;

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    private RestaurantCreateRequestDto validRequest;

    @BeforeEach
    void setUp() {
        validRequest = new RestaurantCreateRequestDto();
        validRequest.setName("Restaurante Ejemplo");
        validRequest.setNit("123456789");
        validRequest.setAddress("Calle 123 #45-67");
        validRequest.setPhone("+573001234567");
        validRequest.setUrlLogo("https://ejemplo.com/logo.png");
        validRequest.setOwnerId(5L);
    }

    @Test
    void createRestaurant_success() {
        when(nitValidationService.isValid(any())).thenReturn(true);
        when(phoneValidationService.isValid(any())).thenReturn(true);
        when(restaurantNameValidationService.isValid(any())).thenReturn(true);
        when(restaurantPersistencePort.existsByNit(any())).thenReturn(false);
        when(userClientPort.getUserRoleById(eq(5L), any())).thenReturn(Collections.singletonList("PROPIETARIO"));
        Restaurante saved = new Restaurante();
        saved.setId(1L);
        saved.setNombre(validRequest.getName());
        saved.setNit(validRequest.getNit());
        saved.setDireccion(validRequest.getAddress());
        saved.setTelefono(validRequest.getPhone());
        saved.setUrlLogo(validRequest.getUrlLogo());
        saved.setFechaCreacion(LocalDateTime.now());
        saved.setFechaActualizacion(LocalDateTime.now());
        when(restaurantPersistencePort.save(any())).thenReturn(saved);
        when(restaurantOwnerPersistencePort.save(any())).thenReturn(new RestauranteOwner());

        Restaurante result = restaurantUseCase.createRestaurant(validRequest, "Bearer token");
        assertNotNull(result);
        assertEquals(validRequest.getName(), result.getNombre());
        verify(restaurantPersistencePort).save(any());
        verify(restaurantOwnerPersistencePort).save(any());
    }

    @Test
    void createRestaurant_invalidNit_throwsException() {
        when(nitValidationService.isValid(any())).thenReturn(false);
        assertThrows(InvalidNitException.class, () ->
            restaurantUseCase.createRestaurant(validRequest, "Bearer token")
        );
    }

    @Test
    void createRestaurant_invalidPhone_throwsException() {
        when(nitValidationService.isValid(any())).thenReturn(true);
        when(phoneValidationService.isValid(any())).thenReturn(false);
        assertThrows(InvalidPhoneException.class, () ->
            restaurantUseCase.createRestaurant(validRequest, "Bearer token")
        );
    }

    @Test
    void createRestaurant_invalidName_throwsException() {
        when(nitValidationService.isValid(any())).thenReturn(true);
        when(phoneValidationService.isValid(any())).thenReturn(true);
        when(restaurantNameValidationService.isValid(any())).thenReturn(false);
        assertThrows(InvalidRestaurantNameException.class, () ->
            restaurantUseCase.createRestaurant(validRequest, "Bearer token")
        );
    }

    @Test
    void createRestaurant_ownerNotFound_throwsException() {
        when(nitValidationService.isValid(any())).thenReturn(true);
        when(phoneValidationService.isValid(any())).thenReturn(true);
        when(restaurantNameValidationService.isValid(any())).thenReturn(true);
        when(restaurantPersistencePort.existsByNit(any())).thenReturn(false);
        when(userClientPort.getUserRoleById(eq(5L), any())).thenReturn(Collections.singletonList("NOT_OWNER"));
        assertThrows(OwnerNotFoundException.class, () ->
            restaurantUseCase.createRestaurant(validRequest, "Bearer token")
        );
    }
}
