package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plazoleta.plazoleta_service.application.port.in.DishServicePort;
import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.DishCreateRequestDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DishController.class)
@AutoConfigureMockMvc(addFilters = false)
class DishControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DishServicePort dishServicePort;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createDish_success() throws Exception {
        DishCreateRequestDto dto = new DishCreateRequestDto();
        dto.setNombre("Pizza");
        dto.setPrecio("12000");
        dto.setDescripcion("Pizza de queso");
        dto.setUrlImagen("http://img.com/pizza.jpg");
        dto.setCategoria("Italiana");
        dto.setRestauranteId(1L);
        Plato plato = new Plato();
        Mockito.when(dishServicePort.createDish(any(DishCreateRequestDto.class), eq("Bearer validtoken"))).thenReturn(plato);

        ResultActions result = mockMvc.perform(post("/api/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer validtoken")
                .content(objectMapper.writeValueAsString(dto)));

        result.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createDish_invalidInput_returnsBadRequest() throws Exception {
        DishCreateRequestDto dto = new DishCreateRequestDto(); // campos vacíos
        mockMvc.perform(post("/api/dishes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer validtoken")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
