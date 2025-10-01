package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.plazoleta.plazoleta_service.application.port.in.OrderCreateUseCasePort;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.OrderCreateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Endpoints for order management")
public class OrderController {
    private final OrderCreateUseCasePort orderCreateUseCasePort;

    @PostMapping
    @Operation(
        summary = "Create a new order (HU-11)",
        description = "Allows a CLIENT to create a new order by providing the required fields. The clientId is extracted from the Bearer token."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Order created successfully", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid input or business rule violation", content = @Content),
        @ApiResponse(responseCode = "401", description = "Invalid or missing token", content = @Content),
        @ApiResponse(responseCode = "403", description = "User is not authorized to create orders", content = @Content),
        @ApiResponse(responseCode = "404", description = "Restaurant or dish not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<Void> createOrder(@RequestBody OrderCreateRequestDto request,
                                            @RequestHeader("Authorization") String authorization) {
        orderCreateUseCasePort.createOrder(request, authorization);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
