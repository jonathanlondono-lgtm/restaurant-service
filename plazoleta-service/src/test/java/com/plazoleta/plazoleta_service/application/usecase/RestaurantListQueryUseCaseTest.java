package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.port.out.RestaurantListQueryPersistencePort;
import com.plazoleta.plazoleta_service.domain.model.Restaurante;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.RestaurantListResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantListQueryUseCaseTest {
    @Mock
    private RestaurantListQueryPersistencePort restaurantListQueryPersistencePort;

    @InjectMocks
    private RestaurantListQueryUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRestaurants_success() {
        Restaurante restaurante1 = new Restaurante(1L, "Restaurante A", "123456789", "Calle 123", "3001234567", "http://logo.com/a.png", null, null);
        Restaurante restaurante2 = new Restaurante(2L, "Restaurante B", "987654321", "Avenida 456", "3007654321", "http://logo.com/b.png", null, null);
        List<Restaurante> restaurantes = Arrays.asList(restaurante1, restaurante2);
        when(restaurantListQueryPersistencePort.getAllRestaurants()).thenReturn(restaurantes);

        List<RestaurantListResponseDto> result = useCase.getAllRestaurants();

        assertEquals(2, result.size());
        assertEquals("Restaurante A", result.get(0).getNombre());
        assertEquals("Restaurante B", result.get(1).getNombre());
        assertEquals("Calle 123", result.get(0).getDireccion());
        assertEquals("Avenida 456", result.get(1).getDireccion());
        assertEquals("3001234567", result.get(0).getTelefono());
        assertEquals("3007654321", result.get(1).getTelefono());
        assertEquals("http://logo.com/a.png", result.get(0).getUrlLogo());
        assertEquals("http://logo.com/b.png", result.get(1).getUrlLogo());
        verify(restaurantListQueryPersistencePort).getAllRestaurants();
    }

    @Test
    void getAllRestaurants_emptyList() {
        when(restaurantListQueryPersistencePort.getAllRestaurants()).thenReturn(List.of());
        List<RestaurantListResponseDto> result = useCase.getAllRestaurants();
        assertTrue(result.isEmpty());
        verify(restaurantListQueryPersistencePort).getAllRestaurants();
    }
}
