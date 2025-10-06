package com.plazoleta.plazoleta_service.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliverOrderRequestDto {
    @NotNull(message = "orderId no puede ser null")
    private Long orderId;

    @NotNull(message = "pinSeguridad no puede ser null")
    private String pinSeguridad;
}
