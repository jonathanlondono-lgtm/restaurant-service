package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.domain.model.Restaurante;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.RestaurantEntityMapper;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantJpaAdapterTest {
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private RestaurantEntityMapper restaurantEntityMapper;
    @InjectMocks
    private RestaurantJpaAdapter restaurantJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurantJpaAdapter = new RestaurantJpaAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Test
    void testSave() {
        Restaurante restaurante = new Restaurante();
        RestaurantEntity entity = new RestaurantEntity();
        RestaurantEntity savedEntity = new RestaurantEntity();
        Restaurante expected = new Restaurante();

        when(restaurantEntityMapper.toEntity(restaurante)).thenReturn(entity);
        when(restaurantRepository.save(entity)).thenReturn(savedEntity);
        when(restaurantEntityMapper.toDomain(savedEntity)).thenReturn(expected);

        Restaurante result = restaurantJpaAdapter.save(restaurante);
        assertEquals(expected, result);
        verify(restaurantEntityMapper).toEntity(restaurante);
        verify(restaurantRepository).save(entity);
        verify(restaurantEntityMapper).toDomain(savedEntity);
    }

    @Test
    void testExistsByNit() {
        String nit = "123456789";
        when(restaurantRepository.existsByNit(nit)).thenReturn(true);
        boolean exists = restaurantJpaAdapter.existsByNit(nit);
        assertTrue(exists);
        verify(restaurantRepository).existsByNit(nit);
    }
}

