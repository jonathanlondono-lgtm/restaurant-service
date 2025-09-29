package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plazoleta.plazoleta_service.application.port.in.DishUpdateServiceport;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.DishUpdateRequestDto;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.DishUpdateResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DishControllerUpdate.class)
@AutoConfigureMockMvc(addFilters = false)
class DishControllerUpdateTest {
    @Autowired MockMvc mockMvc;
    @MockBean DishUpdateServiceport dishUpdateServiceport;
    @Autowired ObjectMapper objectMapper;

    DishUpdateRequestDto request;
    DishUpdateResponseDto response;

    @BeforeEach
    void setUp() {
        request = new DishUpdateRequestDto();
        request.setPlatoId(1L);
        request.setRestauranteId(2L);
        request.setPrecio(BigDecimal.valueOf(18000));
        request.setDescripcion("desc actualizada");
        response = new DishUpdateResponseDto(1L, "Pizza", BigDecimal.valueOf(18000), "desc actualizada", "url", "cat", true, 2L);
    }

    @Test
    void updateDish_success() throws Exception {
        Mockito.when(dishUpdateServiceport.updateDish(any(DishUpdateRequestDto.class), eq("Bearer token"))).thenReturn(response);
        mockMvc.perform(put("/api/dishes/update")
                .header("Authorization", "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.precio").value(18000))
                .andExpect(jsonPath("$.descripcion").value("desc actualizada"));
    }

    @Test
    void updateDish_invalidInput_returnsBadRequest() throws Exception {
        request.setPrecio(null);
        Mockito.when(dishUpdateServiceport.updateDish(any(DishUpdateRequestDto.class), any())).thenThrow(new IllegalArgumentException("Price must be a positive integer"));
        mockMvc.perform(put("/api/dishes/update")
                .header("Authorization", "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
