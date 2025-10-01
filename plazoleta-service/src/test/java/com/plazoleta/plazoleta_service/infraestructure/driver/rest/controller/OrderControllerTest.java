package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.plazoleta.plazoleta_service.application.port.in.OrderCreateUseCasePort;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.OrderCreateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {
    @Mock
    private OrderCreateUseCasePort orderCreateUseCasePort;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_success() {
        OrderCreateRequestDto requestDto = new OrderCreateRequestDto();
        String authorization = "Bearer token";

        doNothing().when(orderCreateUseCasePort).createOrder(requestDto, authorization);

        ResponseEntity<Void> response = orderController.createOrder(requestDto, authorization);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(orderCreateUseCasePort, times(1)).createOrder(requestDto, authorization);
    }
}

