package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plazoleta.plazoleta_service.application.port.in.OrderListQueryPort;
import com.plazoleta.plazoleta_service.application.dto.OrderPageResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderListControllerTest {

    private MockMvc mockMvc;
    @Mock
    private OrderListQueryPort orderListQueryPort;
    @InjectMocks
    private OrderListController orderListController;
    private ObjectMapper objectMapper;
    private OrderPageResponseDto mockResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderListController).build();
        objectMapper = new ObjectMapper();
        mockResponse = new OrderPageResponseDto();
    }

    @Test
    void getOrders_success() throws Exception {
        when(orderListQueryPort.getOrdersByRestaurant(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(mockResponse);

        mockMvc.perform(get("/api/orders/list")
                .header("Authorization", "Bearer token")
                .param("estado", "PENDIENTE")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getOrders_missingToken_returns401() throws Exception {
        mockMvc.perform(get("/api/orders/list")
                .param("estado", "PENDIENTE")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}
