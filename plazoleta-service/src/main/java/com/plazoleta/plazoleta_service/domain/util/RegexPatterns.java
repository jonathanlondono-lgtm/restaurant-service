package com.plazoleta.plazoleta_service.domain.util;

public class RegexPatterns {
    private RegexPatterns(){

    }

    public static final String NIT = "^\\d+$";
    public static final String PHONE = "^\\+?\\d{1,13}$";
    public static final String RESTAURANT_NAME_VALID = "^(?=.*[A-Za-z])[A-Za-z0-9 ]+$";
    public static final String ONLY_NUMBERS = "^\\d+$";
}
