package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.plazoleta.plazoleta_service.application.dto.DeliverOrderRequestDto;
import com.plazoleta.plazoleta_service.application.port.in.DeliverOrderServicePort;
import com.plazoleta.plazoleta_service.domain.util.ExceptionMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(
        name = "Pedidos",
        description = "Endpoints relacionados con la entrega de pedidos a clientes"
)
public class DeliverOrderController {

    private final DeliverOrderServicePort deliverOrderServicePort;
    @Operation(
            summary = "Confirmar entrega de un pedido",
            description = """
                Este endpoint permite al restaurante confirmar la entrega de un pedido al cliente.
                El restaurante debe enviar el **ID del pedido** y el **PIN de seguridad** provisto por el cliente.
                Si el PIN coincide y el pedido está en estado 'LISTO', se marcará como 'ENTREGADO'.
                """
    )
    @ApiResponse(responseCode = "200", description = "Pedido entregado correctamente")
    @ApiResponse(
            responseCode = "400",
            description = "El PIN de seguridad no es válido",
            content = @Content(schema = @Schema(example = "{\"message\": \"" + ExceptionMessages.PIN_NOT_VALID + "\"}"))
    )
    @ApiResponse(responseCode = "404", description = "El pedido no existe o no está listo para entregar")
    @ApiResponse(responseCode = "401", description = "Token inválido o no autorizado")
    @PostMapping("/deliver")
    public ResponseEntity<String> deliverOrder(
            @Parameter(description = "Datos del pedido y PIN de seguridad", required = true)
            @Valid @RequestBody DeliverOrderRequestDto request,

            @Parameter(description = "Token JWT del restaurante", required = true)
            @RequestHeader("Authorization") String bearerToken
    ) {
        deliverOrderServicePort.deliverOrder(request, bearerToken);
        return ResponseEntity.status(HttpStatus.OK).body("Pedido entregado correctamente.");
    }

}
