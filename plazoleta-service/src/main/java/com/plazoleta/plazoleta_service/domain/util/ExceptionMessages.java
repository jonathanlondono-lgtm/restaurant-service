package com.plazoleta.plazoleta_service.domain.util;

public class ExceptionMessages {
    private ExceptionMessages(){}

    public static final String INVALID_NIT = "NIT must contain only numbers.";
    public static final String INVALID_PHONE = "Phone must contain only numbers, up to 13 characters and may start with '+'. Example: +573005698325.";
    public static final String INVALID_RESTAURANT_NAME = "Restaurant name must contain letters and may have numbers, but cannot be only numbers.";
    public static final String NOT_ACCES = "You don't have access to this resource.";
    public static final String INVALID_CREDENTIALS = "Invalid username or password.";

    public static final String INVALID_DISH_NAME = "Dish name is required.";
    public static final String INVALID_DISH_DESCRIPTION = "Description is required.";
    public static final String INVALID_DISH_URL = "Image URL is required.";
    public static final String INVALID_DISH_CATEGORY = "Category is required.";
    public static final String INVALID_DISH_RESTAURANT_ID = "Restaurant ID is required.";
    public static final String INVALID_DISH_PRICE = "Price must be a positive integer greater than 0.";
    public static final String INVALID_DISH_FIELD = "Dish id, restaurant id and enabled flag are required.";
    public static final String DISH_NOT_FOUND_FOR_RESTAURANT = "Dish not found for this restaurant.";
    public static final String RESTAURANT_NOT_FOUND = "Restaurant not found.";
    public static final String DISH_NOT_FOUND_IN_RESTAURANT = "One or more dishes not found in restaurant.";
    public static final String CLIENT_HAS_ACTIVE_ORDER = "Client has active order.";
    public static final String INVALID_ORDER_DETAIL_DISH_ID = "Dish ID in order detail is null for this order.";
}
