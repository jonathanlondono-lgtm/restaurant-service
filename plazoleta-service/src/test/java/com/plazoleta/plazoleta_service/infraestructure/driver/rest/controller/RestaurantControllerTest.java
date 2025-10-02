package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plazoleta.plazoleta_service.application.port.in.RestaurantServicePort;
import com.plazoleta.plazoleta_service.domain.model.Restaurante;
import com.plazoleta.plazoleta_service.application.dto.RestaurantCreateRequestDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
@AutoConfigureMockMvc(addFilters = false)
class RestaurantControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RestaurantServicePort restaurantServicePort;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createRestaurant_success() throws Exception {
        RestaurantCreateRequestDto requestDto = new RestaurantCreateRequestDto();
        Restaurante restaurante = new Restaurante();
        Mockito.when(restaurantServicePort.createRestaurant(any(RestaurantCreateRequestDto.class), eq("Bearer token")))
                .thenReturn(restaurante);

        mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer token")
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
