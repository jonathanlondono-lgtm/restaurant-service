package com.plazoleta.plazoleta_service.application.dto;

import lombok.Data;

@Data
public class DishEnableDisableRequestDto {
    private Long restauranteId;
    private Long platoId;
    private Boolean enabled;
}

