package com.plazoleta.plazoleta_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDetalle {
    private Long id;
    private Long pedidoId;
    private Long platoId;
    private Integer cantidad;
}

