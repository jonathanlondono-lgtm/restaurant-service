package com.plazoleta.plazoleta_service.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NitPhoneRestaurantValidationServiceTest {
    private NitValidationService nitValidationService;
    private PhoneValidationService phoneValidationService;
    private RestaurantNameValidationService restaurantNameValidationService;

    @BeforeEach
    void setUp() {
        nitValidationService = new NitValidationService();
        phoneValidationService = new PhoneValidationService();
        restaurantNameValidationService = new RestaurantNameValidationService();
    }

    @Test
    void testValidNit() {
        assertTrue(nitValidationService.isValid("123456789"));
    }

    @Test
    void testInvalidNitWithLetters() {
        assertFalse(nitValidationService.isValid("123A56789"));
    }

    @Test
    void testInvalidNitNull() {
        assertFalse(nitValidationService.isValid(null));
    }

    @Test
    void testValidPhoneWithPlus() {
        assertTrue(phoneValidationService.isValid("+573005698325"));
    }

    @Test
    void testValidPhoneWithoutPlus() {
        assertTrue(phoneValidationService.isValid("3005698325"));
    }

    @Test
    void testInvalidPhoneTooLong() {
        assertFalse(phoneValidationService.isValid("+5730056983251234"));
    }

    @Test
    void testInvalidPhoneWithLetters() {
        assertFalse(phoneValidationService.isValid("+57300A698325"));
    }

    @Test
    void testInvalidPhoneNull() {
        assertFalse(phoneValidationService.isValid(null));
    }

    @Test
    void testValidRestaurantNameLetters() {
        assertTrue(restaurantNameValidationService.isValid("Restaurante ABC"));
    }

    @Test
    void testValidRestaurantNameLettersAndNumbers() {
        assertTrue(restaurantNameValidationService.isValid("Restaurante 123"));
    }

    @Test
    void testInvalidRestaurantNameOnlyNumbers() {
        assertFalse(restaurantNameValidationService.isValid("123456"));
    }

    @Test
    void testInvalidRestaurantNameWithSymbols() {
        assertFalse(restaurantNameValidationService.isValid("Rest@urante!"));
    }

    @Test
    void testInvalidRestaurantNameNull() {
        assertFalse(restaurantNameValidationService.isValid(null));
    }
}

