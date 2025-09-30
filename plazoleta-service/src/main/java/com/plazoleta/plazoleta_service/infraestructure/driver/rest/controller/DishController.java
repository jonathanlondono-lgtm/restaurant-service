package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.plazoleta.plazoleta_service.application.port.in.DishServicePort;
import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.DishCreateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/dishes")
@RequiredArgsConstructor
@Tag(name = "Dish", description = "Endpoints for dish management")
public class DishController {
    private final DishServicePort dishServicePort;

    @PostMapping
    @Operation(summary = "Create a new dish (HU-3)", description = "Allows an OWNER to create a new dish for a restaurant. Only the owner can create dishes for their restaurant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dish created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or business rule violation"),
            @ApiResponse(responseCode = "401", description = "Invalid or missing token"),
            @ApiResponse(responseCode = "403", description = "User is not authorized to create dishes for this restaurant"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Plato> createDish(
            @Valid @RequestBody DishCreateRequestDto requestDto,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
        Plato plato = dishServicePort.createDish(requestDto, bearerToken);
        return new ResponseEntity<>(plato, HttpStatus.CREATED);
    }
}
