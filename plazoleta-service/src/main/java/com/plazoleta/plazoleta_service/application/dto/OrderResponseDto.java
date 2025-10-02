package com.plazoleta.plazoleta_service.application.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long id;
    private String estado;
    private String fechaCreacion;
    private Long idCliente;
    private List<OrderDetailResponseDto> detalles;
}