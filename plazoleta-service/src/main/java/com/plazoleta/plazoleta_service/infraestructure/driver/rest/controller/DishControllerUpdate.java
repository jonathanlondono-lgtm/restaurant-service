package com.plazoleta.plazoleta_service.infraestructure.driver.rest.controller;

import com.plazoleta.plazoleta_service.application.port.in.DishUpdateServiceport;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.DishUpdateRequestDto;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.DishUpdateResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dishes")
@RequiredArgsConstructor
@Tag(name = "Dish Update", description = "Endpoints for dish Update operations")
public class DishControllerUpdate {
    private final DishUpdateServiceport dishUpdateServiceport;

    @PutMapping("/update")
    @Operation(summary = "Create a new dish (HU-4)", description = "Allows an OWNER to update  a dish from his restaurant. Only the owner can create dishes for their restaurant.")

    public ResponseEntity<DishUpdateResponseDto> updateDish(
            @Valid @RequestBody DishUpdateRequestDto request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken
    ) {
        DishUpdateResponseDto response = dishUpdateServiceport.updateDish(request, bearerToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

