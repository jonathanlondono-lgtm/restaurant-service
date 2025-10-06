package com.plazoleta.plazoleta_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    private Long id;
    private Long clienteId;
    private Long restauranteId;
    private String estado;
    private Long empleadoAsignadoId;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private String pinSeguridad;
    private List<PedidoDetalle> detalles;
    private String phone;
}
