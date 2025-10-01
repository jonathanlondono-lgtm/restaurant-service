package com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto;

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

