package com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter;

import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.DishEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.mapper.DishEntityMapper;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishListJpaAdapterTest {
    @Mock
    private DishRepository dishRepository;
    @Mock
    private DishEntityMapper dishEntityMapper;

    @InjectMocks
    private DishListJpaAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDishesByRestaurant_withCategory_success() {
        Long restauranteId = 1L;
        String category = "Italiana";
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size);
        DishEntity entity1 = new DishEntity();
        entity1.setId(1L);
        entity1.setNombre("Pizza");
        entity1.setPrecio(new BigDecimal("12000"));
        entity1.setDescripcion("Pizza de queso");
        entity1.setUrlImagen("http://img.com/pizza.jpg");
        entity1.setCategoria("Italiana");
        entity1.setActivo(true);
        DishEntity entity2 = new DishEntity();
        entity2.setId(2L);
        entity2.setNombre("Pasta");
        entity2.setPrecio(new BigDecimal("15000"));
        entity2.setDescripcion("Pasta boloñesa");
        entity2.setUrlImagen("http://img.com/pasta.jpg");
        entity2.setCategoria("Italiana");
        entity2.setActivo(true);
        List<DishEntity> entities = Arrays.asList(entity1, entity2);
        Page<DishEntity> entityPage = new PageImpl<>(entities, pageable, 2);

        Plato plato1 = new Plato(1L, "Pizza", new BigDecimal("12000"), "Pizza de queso", "http://img.com/pizza.jpg", "Italiana", true, restauranteId);
        Plato plato2 = new Plato(2L, "Pasta", new BigDecimal("15000"), "Pasta boloñesa", "http://img.com/pasta.jpg", "Italiana", true, restauranteId);

        when(dishRepository.findByRestauranteIdAndCategoria(restauranteId, category, pageable)).thenReturn(entityPage);
        when(dishEntityMapper.toDomain(entity1)).thenReturn(plato1);
        when(dishEntityMapper.toDomain(entity2)).thenReturn(plato2);

        Page<Plato> result = adapter.getDishesByRestaurant(restauranteId, category, page, size);

        assertEquals(2, result.getContent().size());
        assertEquals("Pizza", result.getContent().get(0).getNombre());
        assertEquals("Pasta", result.getContent().get(1).getNombre());
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        verify(dishRepository).findByRestauranteIdAndCategoria(restauranteId, category, pageable);
        verify(dishEntityMapper).toDomain(entity1);
        verify(dishEntityMapper).toDomain(entity2);
    }

    @Test
    void getDishesByRestaurant_withoutCategory_success() {
        Long restauranteId = 1L;
        String category = null;
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size);
        DishEntity entity1 = new DishEntity();
        entity1.setId(1L);
        entity1.setNombre("Pizza");
        entity1.setPrecio(new BigDecimal("12000"));
        entity1.setDescripcion("Pizza de queso");
        entity1.setUrlImagen("http://img.com/pizza.jpg");
        entity1.setCategoria("Italiana");
        entity1.setActivo(true);
        List<DishEntity> entities = Arrays.asList(entity1);
        Page<DishEntity> entityPage = new PageImpl<>(entities, pageable, 1);

        Plato plato1 = new Plato(1L, "Pizza", new BigDecimal("12000"), "Pizza de queso", "http://img.com/pizza.jpg", "Italiana", true, restauranteId);

        when(dishRepository.findByRestauranteId(restauranteId, pageable)).thenReturn(entityPage);
        when(dishEntityMapper.toDomain(entity1)).thenReturn(plato1);

        Page<Plato> result = adapter.getDishesByRestaurant(restauranteId, category, page, size);

        assertEquals(1, result.getContent().size());
        assertEquals("Pizza", result.getContent().get(0).getNombre());
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        verify(dishRepository).findByRestauranteId(restauranteId, pageable);
        verify(dishEntityMapper).toDomain(entity1);
    }

    @Test
    void getDishesByRestaurant_emptyResult() {
        Long restauranteId = 1L;
        String category = "Italiana";
        int page = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(page, size);
        Page<DishEntity> entityPage = new PageImpl<>(List.of(), pageable, 0);

        when(dishRepository.findByRestauranteIdAndCategoria(restauranteId, category, pageable)).thenReturn(entityPage);

        Page<Plato> result = adapter.getDishesByRestaurant(restauranteId, category, page, size);

        assertTrue(result.getContent().isEmpty());
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getTotalPages());
        verify(dishRepository).findByRestauranteIdAndCategoria(restauranteId, category, pageable);
    }
}
