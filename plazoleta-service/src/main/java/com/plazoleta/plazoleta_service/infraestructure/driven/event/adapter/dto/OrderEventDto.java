package com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventDto {
    private Long pedidoId;
    private Long clienteId;
    private Long restauranteId;
    private String estado;
    private String fecha;
    private String fuente;
    private Long empleadoId; // Opcional
    private String pin;      // Opcional
}

