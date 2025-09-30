package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.plazoleta.plazoleta_service.application.port.in.OwnerRestaurantQueryServicePort;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.RestaurantSummaryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owner/restaurants")
@RequiredArgsConstructor
@Tag(name = "Owner Restaurants", description = "Endpoints for querying restaurants owned by a user")
public class OwnerRestaurantController {
    private final OwnerRestaurantQueryServicePort ownerRestaurantQueryUseCase;

    @GetMapping("/{ownerId}")
    @Operation(summary = "Get restaurants by owner ID", description = "Returns a list of restaurants for which the given user is the owner.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of restaurants returned successfully"),
        @ApiResponse(responseCode = "404", description = "No restaurants found for this owner"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<RestaurantSummaryDto>> getRestaurantsByOwnerId(@PathVariable Long ownerId) {
        List<RestaurantSummaryDto> restaurants = ownerRestaurantQueryUseCase.getRestaurantsByOwnerId(ownerId);
        if (restaurants.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurants);
    }
}
