package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.plazoleta.plazoleta_service.application.dto.OrderAssignRequestDto;
import com.plazoleta.plazoleta_service.application.port.in.OrderAssignUseCasePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderAssignControllerTest {
    @Mock
    private OrderAssignUseCasePort orderAssignUseCasePort;
    @InjectMocks
    private OrderAssignController controller;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void assignOrderToEmployee_success() throws Exception {
        OrderAssignRequestDto dto = new OrderAssignRequestDto();
        dto.setOrderId(1L);
        String json = "{\"orderId\":1}";
        String token = "Bearer validtoken";

        doNothing().when(orderAssignUseCasePort).assignOrderToEmployee(any(OrderAssignRequestDto.class), anyString());

        mockMvc.perform(post("/api/orders/assign")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .content(json))
                .andExpect(status().isNoContent());

        verify(orderAssignUseCasePort).assignOrderToEmployee(any(OrderAssignRequestDto.class), eq(token));
    }

    @Test
    void assignOrderToEmployee_invalidToken_returns401() throws Exception {
        String json = "{\"orderId\":1}";
        mockMvc.perform(post("/api/orders/assign")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is4xxClientError());
    }
}

