package com.plazoleta.plazoleta_service.domain.service;

import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class PinGeneratorService {
    private final Random random = new Random();

    public String generatePin() {
        int pin = random.nextInt(9000) + 1000;
        return String.valueOf(pin);
    }
}
