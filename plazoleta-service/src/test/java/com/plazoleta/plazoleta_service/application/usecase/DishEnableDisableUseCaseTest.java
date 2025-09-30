package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.port.out.DishEnableDisableCommandPort;
import com.plazoleta.plazoleta_service.application.port.out.OwnerRestaurantQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.PlatoQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.TokenServicePort;
import com.plazoleta.plazoleta_service.domain.exception.*;
import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.domain.util.ExceptionMessages;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.DishEnableDisableRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishEnableDisableUseCaseTest {
    @Mock
    private TokenServicePort tokenServicePort;
    @Mock
    private OwnerRestaurantQueryPort ownerRestaurantQueryPort;
    @Mock
    private PlatoQueryPort platoQueryPort;
    @Mock
    private DishEnableDisableCommandPort dishEnableDisableCommandPort;

    @InjectMocks
    private DishEnableDisableUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void enableDisableDish_success() {
        DishEnableDisableRequestDto request = new DishEnableDisableRequestDto();
        request.setPlatoId(1L);
        request.setRestauranteId(2L);
        request.setEnabled(true);
        String token = "Bearer token";
        Long userId = 10L;
        Plato plato = new Plato();

        when(tokenServicePort.extractUserId(token)).thenReturn(userId);
        when(ownerRestaurantQueryPort.isOwnerOfRestaurant(userId, 2L)).thenReturn(true);
        when(platoQueryPort.findPlatoByIdAndRestauranteId(1L, 2L)).thenReturn(plato);

        assertDoesNotThrow(() -> useCase.enableDisableDish(request, token));
        verify(dishEnableDisableCommandPort).updateDishEnabledStatus(plato, true);
    }

    @Test
    void enableDisableDish_ownerNotAuthorized_throwsException() {
        DishEnableDisableRequestDto request = new DishEnableDisableRequestDto();
        request.setPlatoId(1L);
        request.setRestauranteId(2L);
        request.setEnabled(true);
        String token = "Bearer token";
        Long userId = 10L;

        when(tokenServicePort.extractUserId(token)).thenReturn(userId);
        when(ownerRestaurantQueryPort.isOwnerOfRestaurant(userId, 2L)).thenReturn(false);

        OwnerNotAuthorizedException ex = assertThrows(OwnerNotAuthorizedException.class,
                () -> useCase.enableDisableDish(request, token));
        assertEquals(ExceptionMessages.NOT_ACCES, ex.getMessage());
    }

    @Test
    void enableDisableDish_dishNotFound_throwsException() {
        DishEnableDisableRequestDto request = new DishEnableDisableRequestDto();
        request.setPlatoId(1L);
        request.setRestauranteId(2L);
        request.setEnabled(true);
        String token = "Bearer token";
        Long userId = 10L;

        when(tokenServicePort.extractUserId(token)).thenReturn(userId);
        when(ownerRestaurantQueryPort.isOwnerOfRestaurant(userId, 2L)).thenReturn(true);
        when(platoQueryPort.findPlatoByIdAndRestauranteId(1L, 2L)).thenReturn(null);

        DishNotFoundForRestaurantException ex = assertThrows(DishNotFoundForRestaurantException.class,
                () -> useCase.enableDisableDish(request, token));
        assertEquals(ExceptionMessages.DISH_NOT_FOUND_FOR_RESTAURANT, ex.getMessage());
    }

    @Test
    void enableDisableDish_nullPlatoId_throwsException() {
        DishEnableDisableRequestDto request = new DishEnableDisableRequestDto();
        request.setPlatoId(null);
        request.setRestauranteId(2L);
        request.setEnabled(true);
        String token = "Bearer token";

        InvalidDishFieldException ex = assertThrows(InvalidDishFieldException.class,
                () -> useCase.enableDisableDish(request, token));
        assertEquals(ExceptionMessages.INVALID_DISH_FIELD, ex.getMessage());
    }

    @Test
    void enableDisableDish_nullRestauranteId_throwsException() {
        DishEnableDisableRequestDto request = new DishEnableDisableRequestDto();
        request.setPlatoId(1L);
        request.setRestauranteId(null);
        request.setEnabled(true);
        String token = "Bearer token";

        InvalidDishRestaurantIdException ex = assertThrows(InvalidDishRestaurantIdException.class,
                () -> useCase.enableDisableDish(request, token));
        assertEquals(ExceptionMessages.INVALID_DISH_RESTAURANT_ID, ex.getMessage());
    }

    @Test
    void enableDisableDish_nullEnabled_throwsException() {
        DishEnableDisableRequestDto request = new DishEnableDisableRequestDto();
        request.setPlatoId(1L);
        request.setRestauranteId(2L);
        request.setEnabled(null);
        String token = "Bearer token";

        InvalidDishFieldException ex = assertThrows(InvalidDishFieldException.class,
                () -> useCase.enableDisableDish(request, token));
        assertEquals(ExceptionMessages.INVALID_DISH_FIELD, ex.getMessage());
    }
}

