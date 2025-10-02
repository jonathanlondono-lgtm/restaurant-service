package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.DishEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantEntity;
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

class DishQueryJpaAdapterTest {
    @Mock
    private DishRepository dishRepository;
    @InjectMocks
    private DishQueryJpaAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDishNameByIdAndRestaurant_success() {
        Long dishId = 1L;
        Long restaurantId = 2L;
        DishEntity dishEntity = new DishEntity();
        dishEntity.setId(dishId);
        dishEntity.setNombre("Pizza");
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(restaurantId);
        dishEntity.setRestaurante(restaurantEntity);
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dishEntity));
        String result = adapter.getDishNameByIdAndRestaurant(dishId, restaurantId);
        assertEquals("Pizza", result);
        verify(dishRepository).findById(dishId);
    }

    @Test
    void getDishNameByIdAndRestaurant_wrongRestaurant_returnsNull() {
        Long dishId = 1L;
        Long restaurantId = 2L;
        DishEntity dishEntity = new DishEntity();
        dishEntity.setId(dishId);
        dishEntity.setNombre("Pizza");
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(99L); // diferente
        dishEntity.setRestaurante(restaurantEntity);
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dishEntity));
        String result = adapter.getDishNameByIdAndRestaurant(dishId, restaurantId);
        assertNull(result);
        verify(dishRepository).findById(dishId);
    }

    @Test
    void getDishNameByIdAndRestaurant_notFound_returnsNull() {
        Long dishId = 1L;
        Long restaurantId = 2L;
        when(dishRepository.findById(dishId)).thenReturn(Optional.empty());
        String result = adapter.getDishNameByIdAndRestaurant(dishId, restaurantId);
        assertNull(result);
        verify(dishRepository).findById(dishId);
    }

    @Test
    void getDishPriceById_success() {
        Long dishId = 1L;
        DishEntity dishEntity = new DishEntity();
        dishEntity.setId(dishId);
        dishEntity.setPrecio(new BigDecimal("15000"));
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dishEntity));
        BigDecimal result = adapter.getDishPriceById(dishId);
        assertEquals(new BigDecimal("15000"), result);
        verify(dishRepository).findById(dishId);
    }

    @Test
    void getDishPriceById_notFound_returnsNull() {
        Long dishId = 1L;
        when(dishRepository.findById(dishId)).thenReturn(Optional.empty());
        BigDecimal result = adapter.getDishPriceById(dishId);
        assertNull(result);
        verify(dishRepository).findById(dishId);
    }
}

