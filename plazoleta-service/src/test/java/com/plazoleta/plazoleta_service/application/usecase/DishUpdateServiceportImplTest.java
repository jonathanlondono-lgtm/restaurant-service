package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.port.out.OwnerRestaurantQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.PlatoCommandPort;
import com.plazoleta.plazoleta_service.application.port.out.PlatoQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.TokenServicePort;
import com.plazoleta.plazoleta_service.domain.exception.InvalidDishPriceException;
import com.plazoleta.plazoleta_service.domain.exception.OwnerNotAuthorizedException;
import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.DishUpdateRequestDto;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.DishUpdateResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishUpdateServiceportImplTest {
    @Mock TokenServicePort tokenServicePort;
    @Mock OwnerRestaurantQueryPort ownerRestaurantQueryPort;
    @Mock PlatoQueryPort platoQueryPort;
    @Mock PlatoCommandPort platoCommandPort;
    @InjectMocks DishUpdateServiceportImpl useCase;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void updateDish_success() {
        DishUpdateRequestDto request = new DishUpdateRequestDto();
        request.setPlatoId(1L);
        request.setRestauranteId(2L);
        request.setPrecio(BigDecimal.valueOf(15000));
        request.setDescripcion("Nueva desc");
        String token = "Bearer token";
        Plato plato = new Plato(1L, "Pizza", BigDecimal.valueOf(12000), "desc", "url", "cat", true, 2L);

        when(tokenServicePort.extractUserId(token)).thenReturn(10L);
        when(ownerRestaurantQueryPort.isOwnerOfRestaurant(10L, 2L)).thenReturn(true);
        when(platoQueryPort.findPlatoByIdAndRestauranteId(1L, 2L)).thenReturn(plato);

        DishUpdateResponseDto response = useCase.updateDish(request, token);

        assertEquals(1L, response.getId());
        assertEquals("Pizza", response.getNombre());
        assertEquals(BigDecimal.valueOf(15000), response.getPrecio());
        assertEquals("Nueva desc", response.getDescripcion());
        verify(platoCommandPort).updatePlato(any(Plato.class));
    }

    @Test
    void updateDish_invalidPrice_throwsException() {
        DishUpdateRequestDto request = new DishUpdateRequestDto();
        request.setPlatoId(1L);
        request.setRestauranteId(2L);
        request.setPrecio(BigDecimal.valueOf(-1));
        request.setDescripcion("desc");
        String token = "Bearer token";

        Exception ex = assertThrows(InvalidDishPriceException.class, () -> useCase.updateDish(request, token));
        assertTrue(ex.getMessage().contains("Price must be a positive integer"));
    }

    @Test
    void updateDish_emptyDescription_throwsException() {
        DishUpdateRequestDto request = new DishUpdateRequestDto();
        request.setPlatoId(1L);
        request.setRestauranteId(2L);
        request.setPrecio(BigDecimal.valueOf(10000));
        request.setDescripcion("");
        String token = "Bearer token";

        Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.updateDish(request, token));
        assertTrue(ex.getMessage().contains("Description is required"));
    }

    @Test
    void updateDish_notOwner_throwsException() {
        DishUpdateRequestDto request = new DishUpdateRequestDto();
        request.setPlatoId(1L);
        request.setRestauranteId(2L);
        request.setPrecio(BigDecimal.valueOf(10000));
        request.setDescripcion("desc");
        String token = "Bearer token";

        when(tokenServicePort.extractUserId(token)).thenReturn(10L);
        when(ownerRestaurantQueryPort.isOwnerOfRestaurant(10L, 2L)).thenReturn(false);

        Exception ex = assertThrows(OwnerNotAuthorizedException.class, () -> useCase.updateDish(request, token));
        assertTrue(ex.getMessage().contains("don't have access"));
    }

    @Test
    void updateDish_platoNotFound_throwsException() {
        DishUpdateRequestDto request = new DishUpdateRequestDto();
        request.setPlatoId(1L);
        request.setRestauranteId(2L);
        request.setPrecio(BigDecimal.valueOf(10000));
        request.setDescripcion("desc");
        String token = "Bearer token";

        when(tokenServicePort.extractUserId(token)).thenReturn(10L);
        when(ownerRestaurantQueryPort.isOwnerOfRestaurant(10L, 2L)).thenReturn(true);
        when(platoQueryPort.findPlatoByIdAndRestauranteId(1L, 2L)).thenReturn(null);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> useCase.updateDish(request, token));
        assertTrue(ex.getMessage().contains("Dish not found"));
    }
}

