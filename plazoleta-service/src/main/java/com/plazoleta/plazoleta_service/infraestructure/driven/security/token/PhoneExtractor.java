package com.plazoleta.plazoleta_service.infraestructure.driven.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class PhoneExtractor implements ClaimExtractor<String>{
    @Override
    public String extract(String bearerToken, String jwtSecret) {
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
            Object restaurantIdObj = claims.get("phone");
            if (restaurantIdObj == null) {
                return null;
            }
            return String.valueOf(restaurantIdObj.toString());
        } catch (Exception e) {
            return null;
        }    }
}
