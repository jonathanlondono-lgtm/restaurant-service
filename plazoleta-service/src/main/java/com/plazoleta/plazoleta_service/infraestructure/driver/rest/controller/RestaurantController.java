package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.plazoleta.plazoleta_service.application.port.in.RestaurantServicePort;
import com.plazoleta.plazoleta_service.domain.model.Restaurante;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.RestaurantCreateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@Tag(name = "Restaurant", description = "Endpoints for restaurant management")
public class RestaurantController {
    private final RestaurantServicePort restaurantServicePort;

    @PostMapping
    @Operation(
        summary = "Create a new restaurant (HU-2)",
        description = "Allows an ADMINISTRATOR to create a new restaurant by providing the required fields. The ownerId must correspond to a user with OWNER role."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Restaurant created successfully",
            content = @Content(schema = @Schema(implementation = Restaurante.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input or business rule violation", content = @Content),
        @ApiResponse(responseCode = "404", description = "Owner user does not exist or does not have OWNER role", content = @Content),
        @ApiResponse(responseCode = "403", description = "User is not authorized to create restaurants", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<Restaurante> createRestaurant(
            @RequestBody RestaurantCreateRequestDto requestDto,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
        Restaurante restaurante = restaurantServicePort.createRestaurant(requestDto, bearerToken);
        return new ResponseEntity<>(restaurante, HttpStatus.CREATED);
    }
}
