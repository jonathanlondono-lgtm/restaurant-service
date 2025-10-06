package com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderReadyNotificationEventDto {
    private Long pedidoId;
    private Long clienteId;
    private Long restauranteId;
    private String fecha;
    private String mensaje;
    private String pin;
    private String telefonoCliente;
}
