package com.plazoleta.plazoleta_service.domain.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PinGeneratorServiceTest {
    @Test
    void generatePin_returnsFourDigitString() {
        PinGeneratorService service = new PinGeneratorService();
        for (int i = 0; i < 100; i++) {
            String pin = service.generatePin();
            assertNotNull(pin);
            assertEquals(4, pin.length(), "El pin debe tener 4 dígitos");
            int pinInt = Integer.parseInt(pin);
            assertTrue(pinInt >= 1000 && pinInt <= 9999, "El pin debe estar entre 1000 y 9999");
        }
    }
}

