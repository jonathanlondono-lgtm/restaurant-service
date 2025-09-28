package com.plazoleta.plazoleta_service.infraestructure.driven.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plazoleta.plazoleta_service.domain.util.ExceptionMessages;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Forbidden");
        body.put("message", ExceptionMessages.NOT_ACCES);
        body.put("timestamp", java.time.LocalDateTime.now().toString());
        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }
}

