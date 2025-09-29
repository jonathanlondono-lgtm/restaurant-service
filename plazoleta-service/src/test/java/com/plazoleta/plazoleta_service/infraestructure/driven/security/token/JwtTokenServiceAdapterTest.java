package com.plazoleta.plazoleta_service.infraestructure.driven.security.token;

import com.plazoleta.plazoleta_service.domain.exception.InvalidTokenException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import javax.crypto.SecretKey;
import static org.junit.jupiter.api.Assertions.*;

class JwtTokenServiceAdapterTest {
    private JwtTokenServiceAdapter jwtTokenServiceAdapter;
    private String secret = "mysecretkeymysecretkeymysecretkey12";

    @BeforeEach
    void setUp() {
        jwtTokenServiceAdapter = new JwtTokenServiceAdapter();
        java.lang.reflect.Field field;
        try {
            field = JwtTokenServiceAdapter.class.getDeclaredField("jwtSecret");
            field.setAccessible(true);
            field.set(jwtTokenServiceAdapter, secret);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void extractUserId_success() {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        String token = Jwts.builder()
                .setSubject("user")
                .claim("userId", 123L)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        String bearer = "Bearer " + token;
        Long userId = jwtTokenServiceAdapter.extractUserId(bearer);
        assertEquals(123L, userId);
    }

    @Test
    void extractUserId_invalidToken() {
        String bearer = "Bearer invalid.token.value";
        assertThrows(InvalidTokenException.class, () -> jwtTokenServiceAdapter.extractUserId(bearer));
    }

    @Test
    void extractUserId_missingUserIdClaim() {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        String token = Jwts.builder()
                .setSubject("user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        String bearer = "Bearer " + token;
        assertThrows(InvalidTokenException.class, () -> jwtTokenServiceAdapter.extractUserId(bearer));
    }

    @Test
    void extractUserId_invalidFormat() {
        String bearer = "invalidtoken";
        assertThrows(InvalidTokenException.class, () -> jwtTokenServiceAdapter.extractUserId(bearer));
    }
}

