package com.plazoleta.plazoleta_service.infraestructure.driven.jpa.adapter;

import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.adapter.DishEnableDisableJpaAdapter;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.entity.DishEntity;
import com.plazoleta.plazoleta_service.infraestructure.driven.db.repository.DishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

class DishEnableDisableJpaAdapterTest {
    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private DishEnableDisableJpaAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateDishEnabledStatus_success() {
        Plato plato = new Plato();
        plato.setId(1L);
        boolean enabled = true;
        DishEntity entity = mock(DishEntity.class);
        when(dishRepository.findById(1L)).thenReturn(Optional.of(entity));

        adapter.updateDishEnabledStatus(plato, enabled);

        verify(entity).setActivo(enabled);
        verify(dishRepository).save(entity);
    }

    @Test
    void updateDishEnabledStatus_dishNotFound() {
        Plato plato = new Plato();
        plato.setId(2L);
        boolean enabled = false;
        when(dishRepository.findById(2L)).thenReturn(Optional.empty());

        adapter.updateDishEnabledStatus(plato, enabled);

        verify(dishRepository, never()).save(any());
    }
}
