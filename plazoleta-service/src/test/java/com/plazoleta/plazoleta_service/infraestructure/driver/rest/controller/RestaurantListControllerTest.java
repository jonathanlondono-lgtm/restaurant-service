package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.plazoleta.plazoleta_service.application.port.in.RestaurantListQueryPort;
import com.plazoleta.plazoleta_service.application.dto.RestaurantListResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantListControllerTest {
    @Mock
    private RestaurantListQueryPort restaurantListQueryPort;

    @InjectMocks
    private RestaurantListController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRestaurants_success() {
        RestaurantListResponseDto dto1 = new RestaurantListResponseDto(1L, "Restaurante A", "Calle 123", "3001234567", "http://logo.com/a.png");
        RestaurantListResponseDto dto2 = new RestaurantListResponseDto(2L, "Restaurante B", "Avenida 456", "3007654321", "http://logo.com/b.png");
        List<RestaurantListResponseDto> dtos = Arrays.asList(dto1, dto2);
        when(restaurantListQueryPort.getAllRestaurants()).thenReturn(dtos);

        ResponseEntity<List<RestaurantListResponseDto>> response = controller.getAllRestaurants();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Restaurante A", response.getBody().get(0).getNombre());
        assertEquals("Restaurante B", response.getBody().get(1).getNombre());
        verify(restaurantListQueryPort).getAllRestaurants();
    }

    @Test
    void getAllRestaurants_emptyList() {
        when(restaurantListQueryPort.getAllRestaurants()).thenReturn(List.of());
        ResponseEntity<List<RestaurantListResponseDto>> response = controller.getAllRestaurants();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(restaurantListQueryPort).getAllRestaurants();
    }
}
