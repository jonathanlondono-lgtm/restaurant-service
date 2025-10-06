package com.plazoleta.plazoleta_service.infraestructure.driven.security.token;

import com.plazoleta.plazoleta_service.application.port.out.TokenServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenServiceAdapter implements TokenServicePort {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private final UserIdExtractor userIdExtractor;
    private final RestaurantIdExtractor restaurantIdExtractor;
    private final PhoneExtractor phoneExtractor = new PhoneExtractor();

    @Autowired
    public JwtTokenServiceAdapter(UserIdExtractor userIdExtractor, RestaurantIdExtractor restaurantIdExtractor) {
        this.userIdExtractor = userIdExtractor;
        this.restaurantIdExtractor = restaurantIdExtractor;
    }

    @Override
    public Long extractUserId(String bearerToken) {
        return userIdExtractor.extract(bearerToken, jwtSecret);
    }
    @Override
    public String extraxtPhoneNumber(String bearerToken) {
        String phone = phoneExtractor.extract(bearerToken, jwtSecret);
        if (phone == null) {
            throw new IllegalArgumentException("Phone ID extracted from token is null");
        }
        return phone;
    }

    @Override
    public Long extractRestaurantId(String bearerToken) {
        Long restaurantId = restaurantIdExtractor.extract(bearerToken, jwtSecret);
        if (restaurantId == null) {
            throw new IllegalArgumentException("Restaurant ID extracted from token is null");
        }
        return restaurantId;
    }


}
