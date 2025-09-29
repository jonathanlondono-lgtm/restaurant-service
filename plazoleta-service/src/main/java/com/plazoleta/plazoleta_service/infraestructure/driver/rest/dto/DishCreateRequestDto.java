package com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class DishCreateRequestDto {
    @NotBlank
    private String nombre;
    @NotNull
    private String precio;
    @NotBlank
    private String descripcion;
    @NotBlank
    private String urlImagen;
    @NotBlank
    private String categoria;
    @NotNull
    private Long restauranteId;
}

