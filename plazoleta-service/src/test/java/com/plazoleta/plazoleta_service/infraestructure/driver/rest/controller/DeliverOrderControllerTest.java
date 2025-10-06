package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plazoleta.plazoleta_service.application.dto.DeliverOrderRequestDto;
import com.plazoleta.plazoleta_service.application.port.in.DeliverOrderServicePort;
import com.plazoleta.plazoleta_service.domain.exception.InvalidSecurityPinException;
import com.plazoleta.plazoleta_service.domain.exception.OrderNotFoundOrNotInPreparationStateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeliverOrderController.class)
@AutoConfigureMockMvc(addFilters = false)
class DeliverOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliverOrderServicePort deliverOrderServicePort;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deliverOrder_Success() throws Exception {
        // Given
        DeliverOrderRequestDto request = new DeliverOrderRequestDto(1L, "1234");
        String bearerToken = "Bearer token";

        doNothing().when(deliverOrderServicePort).deliverOrder(any(DeliverOrderRequestDto.class), eq(bearerToken));

        // When & Then
        mockMvc.perform(post("/api/orders/deliver")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", bearerToken)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Pedido entregado correctamente."));
    }

    @Test
    void deliverOrder_InvalidPin_ReturnsBadRequest() throws Exception {
        // Given
        DeliverOrderRequestDto request = new DeliverOrderRequestDto(1L, "9999");
        String bearerToken = "Bearer token";

        doThrow(new InvalidSecurityPinException("PIN not valid"))
                .when(deliverOrderServicePort).deliverOrder(any(DeliverOrderRequestDto.class), eq(bearerToken));

        // When & Then
        mockMvc.perform(post("/api/orders/deliver")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", bearerToken)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deliverOrder_OrderNotFound_ReturnsBadRequest() throws Exception {
        // Given
        DeliverOrderRequestDto request = new DeliverOrderRequestDto(999L, "1234");
        String bearerToken = "Bearer token";

        doThrow(new OrderNotFoundOrNotInPreparationStateException("Order not found"))
                .when(deliverOrderServicePort).deliverOrder(any(DeliverOrderRequestDto.class), eq(bearerToken));

        // When & Then
        mockMvc.perform(post("/api/orders/deliver")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", bearerToken)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deliverOrder_InvalidInput_ReturnsBadRequest() throws Exception {
        // Given
        DeliverOrderRequestDto request = new DeliverOrderRequestDto(null, null);
        String bearerToken = "Bearer token";

        // When & Then
        mockMvc.perform(post("/api/orders/deliver")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", bearerToken)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deliverOrder_MissingAuthorizationHeader_ReturnsUnauthorized() throws Exception {
        // Given
        DeliverOrderRequestDto request = new DeliverOrderRequestDto(1L, "1234");

        // When & Then
        mockMvc.perform(post("/api/orders/deliver")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
}
