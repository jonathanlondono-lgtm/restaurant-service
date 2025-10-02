package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.port.in.DishListQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.DishListQueryPersistencePort;
import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.application.dto.DishListResponseDto;
import com.plazoleta.plazoleta_service.application.dto.DishPageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishListQueryUseCase implements DishListQueryPort {
    private final DishListQueryPersistencePort dishListQueryPersistencePort;

    @Override
    public DishPageResponseDto getDishesByRestaurant(Long restauranteId, String category, int page, int size) {
        Page<Plato> platoPage = dishListQueryPersistencePort.getDishesByRestaurant(restauranteId, category, page, size);
        List<DishListResponseDto> dishes = platoPage.getContent().stream()
                .map(plato -> new DishListResponseDto(
                        plato.getId(),
                        plato.getNombre(),
                        plato.getPrecio(),
                        plato.getDescripcion(),
                        plato.getUrlImagen(),
                        plato.getCategoria(),
                        plato.getActivo()
                ))
                .toList();
        return new DishPageResponseDto(
                dishes,
                platoPage.getNumber(),
                platoPage.getSize(),
                platoPage.getTotalElements(),
                platoPage.getTotalPages()
        );
    }
}
