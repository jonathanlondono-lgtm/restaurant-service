package com.plazoleta.plazoleta_service.global;

import com.plazoleta.plazoleta_service.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<Object> handleOwnerNotFound(OwnerNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(OwnerNotAuthorizedException.class)
    public ResponseEntity<Object> handleOwnerNotAuthorized(OwnerNotAuthorizedException ex) {
        return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(InvalidNitException.class)
    public ResponseEntity<Object> handleInvalidNit(InvalidNitException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidPhoneException.class)
    public ResponseEntity<Object> handleInvalidPhone(InvalidPhoneException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidRestaurantNameException.class)
    public ResponseEntity<Object> handleInvalidRestaurantName(InvalidRestaurantNameException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Object> handleInvalidToken(InvalidTokenException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneral(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error: " + ex.getMessage());
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(org.springframework.web.bind.MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("message", ex.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .toArray());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDishNameException.class)
    public ResponseEntity<Object> handleInvalidDishName(InvalidDishNameException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidDishDescriptionException.class)
    public ResponseEntity<Object> handleInvalidDishDescription(InvalidDishDescriptionException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidDishUrlException.class)
    public ResponseEntity<Object> handleInvalidDishUrl(InvalidDishUrlException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidDishCategoryException.class)
    public ResponseEntity<Object> handleInvalidDishCategory(InvalidDishCategoryException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidDishRestaurantIdException.class)
    public ResponseEntity<Object> handleInvalidDishRestaurantId(InvalidDishRestaurantIdException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DishNotFoundForRestaurantException.class)
    public ResponseEntity<Object> handleDishNotFoundForRestaurant(DishNotFoundForRestaurantException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidDishPriceException.class)
    public ResponseEntity<Object> handleInvalidDishPrice(InvalidDishPriceException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(InvalidDishFieldException.class)
    public ResponseEntity<Object> handleInvalidDishField(InvalidDishFieldException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DishNotFoundInRestaurantException.class)
    public ResponseEntity<Object> handleDishNotFoundInRestaurant(DishNotFoundInRestaurantException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(InvalidOrderDetailDishIdException.class)
    public ResponseEntity<Object> handleInvalidOrderDetailDishId(InvalidOrderDetailDishIdException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ClientHasActiveOrderException.class)
    public ResponseEntity<Object> handleClientHasActiveOrder(ClientHasActiveOrderException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(InvalidSecurityPinException.class)
    public ResponseEntity<Object> handleInvalidSecurityPin(InvalidSecurityPinException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(OrderNotFoundOrNotInPreparationStateException.class)
    public ResponseEntity<Object> handleOrderNotFoundOrNotInPreparationState(OrderNotFoundOrNotInPreparationStateException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<Object> handleRestaurantNotFound(RestaurantNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(NoPendingOrderToCancelException.class)
    public ResponseEntity<Object> handleNoPendingOrderToCancel(NoPendingOrderToCancelException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(OrderCannotBeCancelledException.class)
    public ResponseEntity<Object> handleOrderCannotBeCancelled(OrderCannotBeCancelledException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}
