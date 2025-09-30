package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.plazoleta.plazoleta_service.application.port.in.DishEnableDisableServicePort;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.DishEnableDisableRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DishEnableDisableControllerTest {
    @Mock
    private DishEnableDisableServicePort dishEnableDisableServicePort;

    @InjectMocks
    private DishEnableDisableController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void enableDisableDish_success() {
        DishEnableDisableRequestDto requestDto = new DishEnableDisableRequestDto();
        requestDto.setPlatoId(1L);
        requestDto.setRestauranteId(2L);
        requestDto.setEnabled(true);
        String bearerToken = "Bearer token";

        doNothing().when(dishEnableDisableServicePort).enableDisableDish(requestDto, bearerToken);

        ResponseEntity<Void> response = controller.enableDisableDish(requestDto, bearerToken);

        assertEquals(200, response.getStatusCodeValue());
        verify(dishEnableDisableServicePort).enableDisableDish(requestDto, bearerToken);
    }
}

