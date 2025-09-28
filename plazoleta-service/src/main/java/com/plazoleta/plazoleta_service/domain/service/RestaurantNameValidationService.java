package com.plazoleta.plazoleta_service.domain.service;

import com.plazoleta.plazoleta_service.domain.util.RegexPatterns;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class RestaurantNameValidationService {
    private static final Pattern PATTERN = Pattern.compile(RegexPatterns.RESTAURANT_NAME_VALID);
    public boolean isValid(String name) {
        return name != null && PATTERN.matcher(name).matches();
    }
}


