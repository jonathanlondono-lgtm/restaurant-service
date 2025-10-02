package com.plazoleta.plazoleta_service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantListResponseDto {
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String urlLogo;
}