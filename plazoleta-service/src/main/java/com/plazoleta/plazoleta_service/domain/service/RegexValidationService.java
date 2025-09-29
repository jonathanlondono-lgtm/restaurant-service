package com.plazoleta.plazoleta_service.domain.service;

import com.plazoleta.plazoleta_service.domain.util.RegexPatterns;
import org.springframework.stereotype.Service;

@Service
public class RegexValidationService {
    public boolean isValid(String value, String pattern) {
        if (value == null) return false;
        return value.matches(pattern);
    }

    public boolean isPositiveInteger(String value) {
        return isValid(value, RegexPatterns.POSITIVE_INTEGER);
    }
}
