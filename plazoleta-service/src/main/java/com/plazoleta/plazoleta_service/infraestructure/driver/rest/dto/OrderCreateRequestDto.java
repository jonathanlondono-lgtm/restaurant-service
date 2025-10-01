package com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequestDto {
    private Long restaurantId;
    private List<DishQuantityDto> dishes;
}
