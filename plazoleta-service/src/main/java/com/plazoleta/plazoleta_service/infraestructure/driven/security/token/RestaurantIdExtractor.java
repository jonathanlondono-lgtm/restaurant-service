package com.plazoleta.plazoleta_service.infraestructure.driven.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class RestaurantIdExtractor implements ClaimExtractor<Long> {
    @Override
    public Long extract(String bearerToken, String jwtSecret) {
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            return null;
        }
        String token = bearerToken.substring(7);
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            Object restaurantIdObj = claims.get("restaurantId");
            if (restaurantIdObj == null) {
                return null;
            }
            return Long.valueOf(restaurantIdObj.toString());
        } catch (Exception e) {
            return null;
        }
    }
}
