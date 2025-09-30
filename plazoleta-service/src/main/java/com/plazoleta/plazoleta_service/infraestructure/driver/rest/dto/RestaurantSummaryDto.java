package com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto;

import lombok.Data;

@Data
public class RestaurantSummaryDto {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String urlLogo;
}

