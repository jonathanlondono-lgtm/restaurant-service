package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.plazoleta.plazoleta_service.application.port.in.DishListQueryPort;
import com.plazoleta.plazoleta_service.application.dto.DishPageResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurants/{restauranteId}/dishes")
@RequiredArgsConstructor
@Tag(name = "Dish List", description = "Endpoints for listing dishes of a restaurant with pagination and category filter")
public class DishListController {
    private final DishListQueryPort dishListQueryPort;

    @GetMapping
    @Operation(summary = "List dishes of a restaurant", description = "Returns a paginated and optionally filtered list of dishes for a restaurant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of dishes returned successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<DishPageResponseDto> getDishesByRestaurant(
            @PathVariable Long restauranteId,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        DishPageResponseDto response = dishListQueryPort.getDishesByRestaurant(restauranteId, category, page, size);
        return ResponseEntity.ok(response);
    }
}

