package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.plazoleta.plazoleta_service.application.port.in.RestaurantListQueryPort;
import com.plazoleta.plazoleta_service.application.dto.RestaurantListResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/list/restaurants")
@RequiredArgsConstructor
@Tag(name = "Restaurant List", description = "Endpoints for listing available restaurants")
public class RestaurantListController {
    private final RestaurantListQueryPort restaurantListQueryPort;

    @GetMapping
    @Operation(summary = "List all restaurants (HU-9)", description = "Returns a list of all available restaurants for clients.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of restaurants returned successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<RestaurantListResponseDto>> getAllRestaurants() {
        List<RestaurantListResponseDto> restaurants = restaurantListQueryPort.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }
}

