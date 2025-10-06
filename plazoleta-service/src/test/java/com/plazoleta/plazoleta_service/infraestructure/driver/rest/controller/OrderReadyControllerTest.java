package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plazoleta.plazoleta_service.application.dto.OrderAssignRequestDto;
import com.plazoleta.plazoleta_service.application.port.in.OrderReadyUseCasePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderReadyController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderReadyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderReadyUseCasePort orderReadyUseCasePort;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void markOrderAsReady_ReturnsOk() throws Exception {
        OrderAssignRequestDto requestDto = new OrderAssignRequestDto();
        // set campos necesarios en requestDto si aplica

        doNothing().when(orderReadyUseCasePort).markOrderAsReady(requestDto, "Bearer token");

        mockMvc.perform(put("/api/orders/ready")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk());
    }
}