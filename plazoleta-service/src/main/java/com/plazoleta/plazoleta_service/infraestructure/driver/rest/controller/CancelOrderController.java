package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.plazoleta.plazoleta_service.application.port.in.CancelOrderServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Cancel Order", description = "Operations related to order cancellation")
@SecurityRequirement(name = "bearerAuth")
public class CancelOrderController {

    private final CancelOrderServicePort cancelOrderServicePort;

    @PostMapping("/cancel")
    @Operation(
        summary = "Cancel pending order",
        description = "Allows a client to cancel their pending order. Only orders in 'PENDIENTE' status can be cancelled."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Order cancelled successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "No pending order to cancel OR Order already in preparation and cannot be cancelled"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - Invalid or missing Bearer token"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error"
        )
    })
    public ResponseEntity<String> cancelOrder(
            @Parameter(
                description = "Bearer token for client authentication",
                required = true,
                example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            )
            @RequestHeader("Authorization") String authorization) {

        cancelOrderServicePort.cancelOrder(authorization);
        return ResponseEntity.ok("Pedido cancelado correctamente.");
    }
}
