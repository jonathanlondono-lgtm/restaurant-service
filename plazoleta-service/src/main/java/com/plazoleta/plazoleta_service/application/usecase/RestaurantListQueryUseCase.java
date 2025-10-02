package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.port.in.RestaurantListQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.RestaurantListQueryPersistencePort;
import com.plazoleta.plazoleta_service.domain.model.Restaurante;
import com.plazoleta.plazoleta_service.application.dto.RestaurantListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantListQueryUseCase implements RestaurantListQueryPort {
    private final RestaurantListQueryPersistencePort restaurantListQueryPersistencePort;

    @Override
    public List<RestaurantListResponseDto> getAllRestaurants() {
        List<Restaurante> restaurantes = restaurantListQueryPersistencePort.getAllRestaurants();
        return restaurantes.stream()
                .map(restaurante -> new RestaurantListResponseDto(
                        restaurante.getId(),
                        restaurante.getNombre(),
                        restaurante.getDireccion(),
                        restaurante.getTelefono(),
                        restaurante.getUrlLogo()
                ))
                .toList();
    }
}
