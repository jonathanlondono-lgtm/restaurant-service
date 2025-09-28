package com.plazoleta.plazoleta_service.domain.util;

public class ExceptionMessages {
    private ExceptionMessages(){

    }
    public static final String INVALID_NIT = "NIT must contain only numbers.";
    public static final String INVALID_PHONE = "Phone must contain only numbers, up to 13 characters and may start with '+'. Example: +573005698325.";
    public static final String INVALID_RESTAURANT_NAME = "Restaurant name must contain letters and may have numbers, but cannot be only numbers.";
    public static final String NOT_ACCES = "YOU DON'T HAVE ACCESS TO THIS RESOURCE";
    public static final String INVALID_CREDENTIALS = "Invalid username or password";


}
