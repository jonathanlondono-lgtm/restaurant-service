package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.plazoleta.plazoleta_service.application.port.in.DishEnableDisableServicePort;
import com.plazoleta.plazoleta_service.application.dto.DishEnableDisableRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/dishes/enable-disable")
@RequiredArgsConstructor
@Tag(name = "Dish Enable/Disable", description = "Endpoints for enabling or disabling dishes")
public class DishEnableDisableController {

    private final DishEnableDisableServicePort dishEnableDisableServicePort;

    @PutMapping
    @Operation(summary = "Enable or disable a dish", description = "Allows an OWNER to enable or disable a dish for their restaurant. Only the owner can perform this action.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dish status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or business rule violation"),
            @ApiResponse(responseCode = "401", description = "Invalid or missing token"),
            @ApiResponse(responseCode = "403", description = "User is not authorized to update this dish"),
            @ApiResponse(responseCode = "404", description = "Dish or restaurant not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> enableDisableDish(
            @Valid @RequestBody DishEnableDisableRequestDto requestDto,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken) {
        dishEnableDisableServicePort.enableDisableDish(requestDto, bearerToken);
        return ResponseEntity.ok().build();
    }
}

