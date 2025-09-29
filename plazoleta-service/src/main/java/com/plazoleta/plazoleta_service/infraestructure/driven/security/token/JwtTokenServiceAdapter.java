package com.plazoleta.plazoleta_service.infraestructure.driven.security.token;

import com.plazoleta.plazoleta_service.application.port.out.TokenServicePort;
import com.plazoleta.plazoleta_service.domain.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtTokenServiceAdapter implements TokenServicePort {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public Long extractUserId(String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new InvalidTokenException("Invalid or missing token");
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
                throw new InvalidTokenException("userId claim not found in token");
            }
            return Long.valueOf(userIdObj.toString());
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid JWT token: " + e.getMessage(), e);
        }
    }
}
