package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.port.in.OwnerRestaurantQueryServicePort;
import com.plazoleta.plazoleta_service.application.port.out.OwnerRestaurantListQueryPort;
import com.plazoleta.plazoleta_service.domain.model.Restaurante;
import com.plazoleta.plazoleta_service.infraestructure.driver.rest.dto.RestaurantSummaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerRestaurantListQueryUseCase implements OwnerRestaurantQueryServicePort {
    private final OwnerRestaurantListQueryPort ownerRestaurantListQueryPort;

    @Override
    public List<RestaurantSummaryDto> getRestaurantsByOwnerId(Long ownerId) {
        List<Restaurante> restaurantes = ownerRestaurantListQueryPort.findRestaurantsByOwnerId(ownerId);
        return restaurantes.stream().map(r -> {
            RestaurantSummaryDto dto = new RestaurantSummaryDto();
            dto.setId(r.getId());
            dto.setName(r.getNombre());
            dto.setAddress(r.getDireccion());
            dto.setPhone(r.getTelefono());
            dto.setUrlLogo(r.getUrlLogo());
            return dto;
        }).collect(Collectors.toList());
    }
}
