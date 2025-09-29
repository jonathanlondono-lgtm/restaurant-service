package com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishUpdateResponseDto {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private String descripcion;
    private String urlImagen;
    private String categoria;
    private Boolean activo;
    private Long restauranteId;
}

