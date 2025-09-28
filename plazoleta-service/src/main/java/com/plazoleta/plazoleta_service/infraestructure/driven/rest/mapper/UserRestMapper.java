package com.plazoleta.plazoleta_service.infraestructure.driven.rest.mapper;

import com.plazoleta.plazoleta_service.infraestructure.driven.rest.dto.RoleResponseDto;
import java.util.List;
import java.util.stream.Collectors;

public class UserRestMapper {

    public List<String> toRoleNames(RoleResponseDto[] dtos) {
        if (dtos == null) return List.of();
        return java.util.Arrays.stream(dtos)
                .map(RoleResponseDto::getName)
                .collect(Collectors.toList());
    }
}
