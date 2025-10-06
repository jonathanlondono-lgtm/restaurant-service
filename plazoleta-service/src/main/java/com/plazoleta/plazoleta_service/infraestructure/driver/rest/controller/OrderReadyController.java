package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.plazoleta.plazoleta_service.application.dto.OrderAssignRequestDto;
import com.plazoleta.plazoleta_service.application.port.in.OrderReadyUseCasePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/orders/ready")
@RequiredArgsConstructor
@Tag(name = "Order Ready", description = "Endpoint para marcar un pedido como listo")
public class OrderReadyController {
    private final OrderReadyUseCasePort orderReadyUseCasePort;

    @Operation(summary = "Marcar pedido como listo", description = "Cambia el estado de un pedido a LISTO y envía el evento de trazabilidad.")
    @PutMapping
    public ResponseEntity<Void> markOrderAsReady(@RequestBody OrderAssignRequestDto requestDto,
                                                 @RequestHeader("Authorization") String bearerToken) {
        orderReadyUseCasePort.markOrderAsReady(requestDto, bearerToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

