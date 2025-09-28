package com.plazoleta.plazoleta_service.domain.service;

import com.plazoleta.plazoleta_service.domain.util.RegexPatterns;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class NitValidationService {
    private static final Pattern PATTERN = Pattern.compile(RegexPatterns.NIT);
    public boolean isValid(String nit) {
        return nit != null && PATTERN.matcher(nit).matches();
    }
}