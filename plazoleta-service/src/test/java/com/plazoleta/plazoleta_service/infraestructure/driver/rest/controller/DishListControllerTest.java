package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.plazoleta.plazoleta_service.application.port.in.DishListQueryPort;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.DishPageResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishListControllerTest {
    @Mock
    private DishListQueryPort dishListQueryPort;

    @InjectMocks
    private DishListController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDishesByRestaurant_success() {
        Long restauranteId = 1L;
        String category = "Italiana";
        int page = 0;
        int size = 2;
        DishPageResponseDto responseDto = new DishPageResponseDto(List.of(), page, size, 0, 0);
        when(dishListQueryPort.getDishesByRestaurant(restauranteId, category, page, size)).thenReturn(responseDto);

        ResponseEntity<DishPageResponseDto> response = controller.getDishesByRestaurant(restauranteId, category, page, size);

        assertEquals(org.springframework.http.HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
        verify(dishListQueryPort).getDishesByRestaurant(restauranteId, category, page, size);
    }

    @Test
    void getDishesByRestaurant_emptyResult() {
        Long restauranteId = 1L;
        String category = null;
        int page = 1;
        int size = 5;
        DishPageResponseDto responseDto = new DishPageResponseDto(List.of(), page, size, 0, 0);
        when(dishListQueryPort.getDishesByRestaurant(restauranteId, category, page, size)).thenReturn(responseDto);

        ResponseEntity<DishPageResponseDto> response = controller.getDishesByRestaurant(restauranteId, category, page, size);

        assertEquals(org.springframework.http.HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getDishes().isEmpty());
        verify(dishListQueryPort).getDishesByRestaurant(restauranteId, category, page, size);
    }
}

