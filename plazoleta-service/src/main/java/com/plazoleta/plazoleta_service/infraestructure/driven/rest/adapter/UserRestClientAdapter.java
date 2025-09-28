package com.plazoleta.plazoleta_service.infraestructure.driven.rest.adapter;

import com.plazoleta.plazoleta_service.application.port.out.UserClientPort;
import com.plazoleta.plazoleta_service.infraestructure.driven.rest.dto.RoleResponseDto;
import com.plazoleta.plazoleta_service.infraestructure.driven.rest.exception.UserClientException;
import com.plazoleta.plazoleta_service.infraestructure.driven.rest.mapper.UserRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserRestClientAdapter implements UserClientPort {
    private final RestTemplate restTemplate;
    private final UserRestMapper userRestMapper;
    private static final String USER_SERVICE_URL = "http://localhost:8080";

    @Override
    public List<String> getUserRoleById(Long userId, String bearerToken) {
        String url = USER_SERVICE_URL + "/roles/" + userId;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, bearerToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            RequestEntity<Void> request = new RequestEntity<>(headers, HttpMethod.GET, java.net.URI.create(url));

            ResponseEntity<RoleResponseDto[]> response = restTemplate.exchange(request, RoleResponseDto[].class);
            return userRestMapper.toRoleNames(response.getBody());
        } catch (RestClientException e) {
            throw new UserClientException("Failed to connect to user service for user role validation", e);
        }
    }
}
