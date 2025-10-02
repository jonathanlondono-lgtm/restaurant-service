package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.plazoleta.plazoleta_service.application.port.in.OrderListQueryPort;
import com.plazoleta.plazoleta_service.application.dto.OrderPageResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders/list")
@RequiredArgsConstructor
@Tag(name = "Order List", description = "Endpoints for listing orders by restaurant and status")
public class OrderListController {
    private final OrderListQueryPort orderListQueryPort;

    @GetMapping
    @Operation(
        summary = "Get paginated orders by restaurant and status (HU-12)",
        description = "Allows an EMPLOYEE to list orders of their restaurant, filtered by status and paginated. The restaurantId is extracted from the Bearer token."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orders listed successfully", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
        @ApiResponse(responseCode = "401", description = "Invalid or missing token", content = @Content),
        @ApiResponse(responseCode = "403", description = "User is not authorized to view orders", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public ResponseEntity<OrderPageResponseDto> getOrders(
            @RequestHeader("Authorization") String authorization,
            @RequestParam(required = false) String estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        OrderPageResponseDto response = orderListQueryPort.getOrdersByRestaurant(authorization, estado, page, size);
        return ResponseEntity.ok(response);
    }
}
