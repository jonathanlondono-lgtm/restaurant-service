package com.plazoleta.plazoleta_service.application.port.in;

import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.RestaurantSummaryDto;
import java.util.List;

public interface OwnerRestaurantQueryServicePort {
    List<RestaurantSummaryDto> getRestaurantsByOwnerId(Long ownerId);
}
