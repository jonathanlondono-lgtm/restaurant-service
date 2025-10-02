package com.plazoleta.plazoleta_service.application.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Data
public class DishUpdateRequestDto {
    @NotNull
    private Long platoId;

    @NotNull
    private Long restauranteId;

    @NotNull
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    @NotBlank
    private String descripcion;
}
