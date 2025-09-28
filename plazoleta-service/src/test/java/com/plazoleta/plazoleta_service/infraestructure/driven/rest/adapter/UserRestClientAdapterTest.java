package com.plazoleta.plazoleta_service.infraestructure.driven.rest.adapter;

import com.plazoleta.plazoleta_service.infraestructure.driven.rest.dto.RoleResponseDto;
import com.plazoleta.plazoleta_service.infraestructure.driven.rest.exception.UserClientException;
import com.plazoleta.plazoleta_service.infraestructure.driven.rest.mapper.UserRestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserRestClientAdapterTest {
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private UserRestMapper userRestMapper;
    @InjectMocks
    private UserRestClientAdapter userRestClientAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRestClientAdapter = new UserRestClientAdapter(restTemplate, userRestMapper);
    }

    @Test
    void testGetUserRoleById_Success() {
        Long userId = 1L;
        String bearerToken = "Bearer token";
        String url = "http://localhost:8080/roles/" + userId;
        RoleResponseDto adminDto = new RoleResponseDto();
        adminDto.setName("ADMIN");
        RoleResponseDto userDto = new RoleResponseDto();
        userDto.setName("USER");
        RoleResponseDto[] responseDtos = { adminDto, userDto };
        List<String> expectedRoles = Arrays.asList("ADMIN", "USER");

        when(restTemplate.exchange(any(RequestEntity.class), eq(RoleResponseDto[].class)))
                .thenReturn(ResponseEntity.ok(responseDtos));
        when(userRestMapper.toRoleNames(responseDtos)).thenReturn(expectedRoles);

        List<String> result = userRestClientAdapter.getUserRoleById(userId, bearerToken);
        assertEquals(expectedRoles, result);
        verify(restTemplate).exchange(any(RequestEntity.class), eq(RoleResponseDto[].class));
        verify(userRestMapper).toRoleNames(responseDtos);
    }

    @Test
    void testGetUserRoleById_RestClientException() {
        Long userId = 2L;
        String bearerToken = "Bearer token";
        when(restTemplate.exchange(any(RequestEntity.class), eq(RoleResponseDto[].class)))
                .thenThrow(new RestClientException("Connection error"));

        UserClientException exception = assertThrows(UserClientException.class, () ->
                userRestClientAdapter.getUserRoleById(userId, bearerToken));
        assertTrue(exception.getMessage().contains("Failed to connect to user service"));
        verify(restTemplate).exchange(any(RequestEntity.class), eq(RoleResponseDto[].class));
    }
}
