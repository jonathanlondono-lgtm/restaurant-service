package com.plazoleta.plazoleta_service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishQuantityDto {
    private Long dishId;
    private Integer quantity;
}

