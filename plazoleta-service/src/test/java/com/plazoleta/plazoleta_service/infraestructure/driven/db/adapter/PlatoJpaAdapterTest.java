package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.DishEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.DishEntityMapper;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlatoJpaAdapterTest {
    @Mock DishRepository dishRepository;
    @Mock DishEntityMapper dishEntityMapper;
    @InjectMocks PlatoJpaAdapter adapter;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void findPlatoByIdAndRestauranteId_success() {
        DishEntity entity = new DishEntity();
        entity.setId(1L);
        RestaurantEntity rest = new RestaurantEntity();
        rest.setId(2L);
        entity.setRestaurante(rest);
        Plato plato = new Plato(1L, "Pizza", BigDecimal.valueOf(12000), "desc", "url", "cat", true, 2L);

        when(dishRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(dishEntityMapper.toDomain(entity)).thenReturn(plato);

        Plato result = adapter.findPlatoByIdAndRestauranteId(1L, 2L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(2L, result.getRestauranteId());
    }

    @Test
    void findPlatoByIdAndRestauranteId_notFound_returnsNull() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());
        Plato result = adapter.findPlatoByIdAndRestauranteId(1L, 2L);
        assertNull(result);
    }

    @Test
    void findPlatoByIdAndRestauranteId_wrongRestaurant_returnsNull() {
        DishEntity entity = new DishEntity();
        entity.setId(1L);
        RestaurantEntity rest = new RestaurantEntity();
        rest.setId(99L);
        entity.setRestaurante(rest);
        when(dishRepository.findById(1L)).thenReturn(Optional.of(entity));
        Plato result = adapter.findPlatoByIdAndRestauranteId(1L, 2L);
        assertNull(result);
    }

    @Test
    void updatePlato_success() {
        Plato plato = new Plato(1L, "Pizza", BigDecimal.valueOf(15000), "nueva desc", "url", "cat", true, 2L);
        DishEntity entity = new DishEntity();
        entity.setId(1L);
        entity.setPrecio(BigDecimal.valueOf(12000));
        entity.setDescripcion("desc");
        RestaurantEntity rest = new RestaurantEntity();
        rest.setId(2L);
        entity.setRestaurante(rest);
        when(dishRepository.findById(1L)).thenReturn(Optional.of(entity));

        adapter.updatePlato(plato);

        assertEquals(BigDecimal.valueOf(15000), entity.getPrecio());
        assertEquals("nueva desc", entity.getDescripcion());
        verify(dishRepository).save(entity);
    }
}

