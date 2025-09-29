package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.DishEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.DishEntityMapper;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.DishRepository;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DishJpaAdapterTest {
    @Mock
    private DishRepository dishRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private DishEntityMapper dishEntityMapper;
    @InjectMocks
    private DishJpaAdapter dishJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dishJpaAdapter = new DishJpaAdapter(dishRepository, restaurantRepository, dishEntityMapper);
    }

    @Test
    void saveDish_success() {
        Long restauranteId = 1L;
        Plato plato = new Plato();
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        DishEntity dishEntity = new DishEntity();
        DishEntity savedEntity = new DishEntity();
        Plato expected = new Plato();

        when(restaurantRepository.findById(restauranteId)).thenReturn(Optional.of(restaurantEntity));
        when(dishEntityMapper.toEntity(plato, restaurantEntity)).thenReturn(dishEntity);
        when(dishRepository.save(dishEntity)).thenReturn(savedEntity);
        when(dishEntityMapper.toDomain(savedEntity)).thenReturn(expected);

        Plato result = dishJpaAdapter.saveDish(restauranteId, plato);
        assertEquals(expected, result);
        verify(restaurantRepository).findById(restauranteId);
        verify(dishEntityMapper).toEntity(plato, restaurantEntity);
        verify(dishRepository).save(dishEntity);
        verify(dishEntityMapper).toDomain(savedEntity);
    }

    @Test
    void saveDish_restaurantNotFound() {
        Long restauranteId = 2L;
        Plato plato = new Plato();
        when(restaurantRepository.findById(restauranteId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> dishJpaAdapter.saveDish(restauranteId, plato));
        verify(restaurantRepository).findById(restauranteId);
        verifyNoMoreInteractions(dishEntityMapper, dishRepository);
    }
}

