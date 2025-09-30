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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantListJpaAdapterTest {
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private RestaurantEntityMapper restaurantEntityMapper;

    @InjectMocks
    private RestaurantListJpaAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRestaurants_success() {
        RestaurantEntity entity1 = new RestaurantEntity();
        entity1.setId(1L);
        entity1.setNombre("Restaurante A");
        entity1.setDireccion("Calle 123");
        entity1.setTelefono("3001234567");
        entity1.setUrlLogo("http://logo.com/a.png");
        RestaurantEntity entity2 = new RestaurantEntity();
        entity2.setId(2L);
        entity2.setNombre("Restaurante B");
        entity2.setDireccion("Avenida 456");
        entity2.setTelefono("3007654321");
        entity2.setUrlLogo("http://logo.com/b.png");
        List<RestaurantEntity> entities = Arrays.asList(entity1, entity2);

        Restaurante restaurante1 = new Restaurante(1L, "Restaurante A", null, "Calle 123", "3001234567", "http://logo.com/a.png", null, null);
        Restaurante restaurante2 = new Restaurante(2L, "Restaurante B", null, "Avenida 456", "3007654321", "http://logo.com/b.png", null, null);

        when(restaurantRepository.findAll()).thenReturn(entities);
        when(restaurantEntityMapper.toDomain(entity1)).thenReturn(restaurante1);
        when(restaurantEntityMapper.toDomain(entity2)).thenReturn(restaurante2);

        List<Restaurante> result = adapter.getAllRestaurants();

        assertEquals(2, result.size());
        assertEquals("Restaurante A", result.get(0).getNombre());
        assertEquals("Restaurante B", result.get(1).getNombre());
        assertEquals("Calle 123", result.get(0).getDireccion());
        assertEquals("Avenida 456", result.get(1).getDireccion());
        assertEquals("3001234567", result.get(0).getTelefono());
        assertEquals("3007654321", result.get(1).getTelefono());
        assertEquals("http://logo.com/a.png", result.get(0).getUrlLogo());
        assertEquals("http://logo.com/b.png", result.get(1).getUrlLogo());
        verify(restaurantRepository).findAll();
        verify(restaurantEntityMapper).toDomain(entity1);
        verify(restaurantEntityMapper).toDomain(entity2);
    }

    @Test
    void getAllRestaurants_emptyList() {
        when(restaurantRepository.findAll()).thenReturn(List.of());
        List<Restaurante> result = adapter.getAllRestaurants();
        assertTrue(result.isEmpty());
        verify(restaurantRepository).findAll();
    }
}

