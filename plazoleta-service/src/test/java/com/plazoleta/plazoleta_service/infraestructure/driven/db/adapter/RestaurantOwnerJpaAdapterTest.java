package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.domain.model.RestauranteOwner;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.RestaurantOwnerEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.RestaurantOwnerEntityMapper;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.RestaurantOwnerRepository;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantOwnerJpaAdapterTest {
    @Mock
    private RestaurantOwnerRepository restaurantOwnerRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private RestaurantOwnerEntityMapper restaurantOwnerEntityMapper;
    @InjectMocks
    private RestaurantOwnerJpaAdapter restaurantOwnerJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurantOwnerJpaAdapter = new RestaurantOwnerJpaAdapter(restaurantOwnerRepository, restaurantRepository, restaurantOwnerEntityMapper);
    }

    @Test
    void testSave_Success() {
        RestauranteOwner restauranteOwner = mock(RestauranteOwner.class);
        Long restauranteId = 1L;
        when(restauranteOwner.getRestauranteId()).thenReturn(restauranteId);
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        when(restaurantRepository.findById(restauranteId)).thenReturn(Optional.of(restaurantEntity));
        RestaurantOwnerEntity ownerEntity = new RestaurantOwnerEntity();
        when(restaurantOwnerEntityMapper.toEntity(restauranteOwner, restaurantEntity)).thenReturn(ownerEntity);
        RestaurantOwnerEntity savedEntity = new RestaurantOwnerEntity();
        when(restaurantOwnerRepository.save(ownerEntity)).thenReturn(savedEntity);
        RestauranteOwner expected = mock(RestauranteOwner.class);
        when(restaurantOwnerEntityMapper.toDomain(savedEntity)).thenReturn(expected);

        RestauranteOwner result = restaurantOwnerJpaAdapter.save(restauranteOwner);
        assertEquals(expected, result);
        verify(restaurantRepository).findById(restauranteId);
        verify(restaurantOwnerEntityMapper).toEntity(restauranteOwner, restaurantEntity);
        verify(restaurantOwnerRepository).save(ownerEntity);
        verify(restaurantOwnerEntityMapper).toDomain(savedEntity);
    }

    @Test
    void testSave_RestaurantNotFound() {
        RestauranteOwner restauranteOwner = mock(RestauranteOwner.class);
        Long restauranteId = 2L;
        when(restauranteOwner.getRestauranteId()).thenReturn(restauranteId);
        when(restaurantRepository.findById(restauranteId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            restaurantOwnerJpaAdapter.save(restauranteOwner);
        });
        assertEquals("Restaurant not found for owner relation", exception.getMessage());
        verify(restaurantRepository).findById(restauranteId);
        verifyNoMoreInteractions(restaurantOwnerEntityMapper, restaurantOwnerRepository);
    }
}

