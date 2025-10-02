package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.port.out.DishListQueryPersistencePort;
import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.application.dto.DishPageResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishListQueryUseCaseTest {
    @Mock
    private DishListQueryPersistencePort dishListQueryPersistencePort;

    @InjectMocks
    private DishListQueryUseCase useCase;

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
        Plato plato1 = new Plato(1L, "Pizza", new BigDecimal("12000"), "Pizza de queso", "http://img.com/pizza.jpg", "Italiana", true, restauranteId);
        Plato plato2 = new Plato(2L, "Pasta", new BigDecimal("15000"), "Pasta boloñesa", "http://img.com/pasta.jpg", "Italiana", true, restauranteId);
        List<Plato> platos = Arrays.asList(plato1, plato2);
        Page<Plato> platoPage = new PageImpl<>(platos, PageRequest.of(page, size), 2);

        when(dishListQueryPersistencePort.getDishesByRestaurant(restauranteId, category, page, size)).thenReturn(platoPage);

        DishPageResponseDto result = useCase.getDishesByRestaurant(restauranteId, category, page, size);

        assertEquals(2, result.getDishes().size());
        assertEquals("Pizza", result.getDishes().get(0).getNombre());
        assertEquals("Pasta", result.getDishes().get(1).getNombre());
        assertEquals(page, result.getPage());
        assertEquals(size, result.getSize());
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        verify(dishListQueryPersistencePort).getDishesByRestaurant(restauranteId, category, page, size);
    }

    @Test
    void getDishesByRestaurant_emptyResult() {
        Long restauranteId = 1L;
        String category = "Italiana";
        int page = 0;
        int size = 2;
        Page<Plato> platoPage = new PageImpl<>(List.of(), PageRequest.of(page, size), 0);

        when(dishListQueryPersistencePort.getDishesByRestaurant(restauranteId, category, page, size)).thenReturn(platoPage);

        DishPageResponseDto result = useCase.getDishesByRestaurant(restauranteId, category, page, size);

        assertTrue(result.getDishes().isEmpty());
        assertEquals(page, result.getPage());
        assertEquals(size, result.getSize());
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getTotalPages());
        verify(dishListQueryPersistencePort).getDishesByRestaurant(restauranteId, category, page, size);
    }
}

