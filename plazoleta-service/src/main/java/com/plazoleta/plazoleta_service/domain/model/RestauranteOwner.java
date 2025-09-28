package com.plazoleta.plazoleta_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteOwner {
    private Long id;
    private Long restauranteId;
    private Long ownerId;
    private String rol;
    private LocalDateTime fechaAsignacion;
}
