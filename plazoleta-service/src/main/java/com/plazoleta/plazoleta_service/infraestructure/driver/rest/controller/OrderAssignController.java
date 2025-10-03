package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.plazoleta.plazoleta_service.application.dto.OrderAssignRequestDto;
import com.plazoleta.plazoleta_service.application.port.in.OrderAssignUseCasePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Assignment", description = "Endpoints for assigning orders to employees")
public class OrderAssignController {
    private final OrderAssignUseCasePort orderAssignUseCasePort;

    @PostMapping("/assign")
    @Operation(
        summary = "Assign an order to an employee and change its status to 'EN_PREPARACION'",
        description = "Allows an EMPLOYEE to assign themselves to a pending order. The employeeId is extracted from the Bearer token."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Order assigned successfully", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid input or business rule violation", content = @Content),
        @ApiResponse(responseCode = "401", description = "Invalid or missing token", content = @Content),
        @ApiResponse(responseCode = "403", description = "User is not authorized to assign orders", content = @Content),
        @ApiResponse(responseCode = "404", description = "Order not found or not in PENDING state", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<Void> assignOrderToEmployee(
            @RequestBody OrderAssignRequestDto requestDto,
            @RequestHeader("Authorization") String bearerToken) {
        orderAssignUseCasePort.assignOrderToEmployee(requestDto, bearerToken);
        return ResponseEntity.noContent().build();
    }
}
