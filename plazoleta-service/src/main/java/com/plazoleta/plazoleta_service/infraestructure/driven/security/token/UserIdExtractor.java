package com.plazoleta.plazoleta_service.infraestructure.driven.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class UserIdExtractor implements ClaimExtractor<Long> {
    @Override
    public Long extract(String bearerToken, String jwtSecret) {
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid or missing token");
        }
        String token = bearerToken.substring(7);
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            Object userIdObj = claims.get("userId");
            if (userIdObj == null) {
                throw new IllegalArgumentException("userId claim not found in token");
            }
            return Long.valueOf(userIdObj.toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT token: " + e.getMessage(), e);
        }
    }
}
