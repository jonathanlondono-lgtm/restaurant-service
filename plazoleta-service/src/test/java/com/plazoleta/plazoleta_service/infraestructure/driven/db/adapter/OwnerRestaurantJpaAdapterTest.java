package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.RestaurantOwnerEntityMapper;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.RestaurantOwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class OwnerRestaurantJpaAdapterTest {
    @Mock
    private RestaurantOwnerRepository restaurantOwnerRepository;
    @Mock
    private RestaurantOwnerEntityMapper restaurantOwnerEntityMapper;
    @InjectMocks
    private OwnerRestaurantJpaAdapter ownerRestaurantJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ownerRestaurantJpaAdapter = new OwnerRestaurantJpaAdapter(restaurantOwnerRepository, restaurantOwnerEntityMapper);
    }

    @Test
    void isOwnerOfRestaurant_true() {
        when(restaurantOwnerRepository.existsByOwnerIdAndRestaurante_Id(10L, 1L)).thenReturn(true);
        assertTrue(ownerRestaurantJpaAdapter.isOwnerOfRestaurant(10L, 1L));
        verify(restaurantOwnerRepository).existsByOwnerIdAndRestaurante_Id(10L, 1L);
    }

    @Test
    void isOwnerOfRestaurant_false() {
        when(restaurantOwnerRepository.existsByOwnerIdAndRestaurante_Id(20L, 2L)).thenReturn(false);
        assertFalse(ownerRestaurantJpaAdapter.isOwnerOfRestaurant(20L, 2L));
        verify(restaurantOwnerRepository).existsByOwnerIdAndRestaurante_Id(20L, 2L);
    }
}

