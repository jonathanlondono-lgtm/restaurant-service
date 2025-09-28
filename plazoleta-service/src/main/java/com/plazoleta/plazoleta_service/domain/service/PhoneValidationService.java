package com.plazoleta.plazoleta_service.domain.service;

import com.plazoleta.plazoleta_service.domain.util.RegexPatterns;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class PhoneValidationService {
    private static final Pattern PATTERN = Pattern.compile(RegexPatterns.PHONE);
    public boolean isValid(String phone) {
        return phone != null && PATTERN.matcher(phone).matches();
    }
}

