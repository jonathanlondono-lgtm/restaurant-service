package com.plazoleta.plazoleta_service.application.usecase;

import com.plazoleta.plazoleta_service.application.port.out.DishPersistencePort;
import com.plazoleta.plazoleta_service.application.port.out.OwnerRestaurantQueryPort;
import com.plazoleta.plazoleta_service.application.port.out.TokenServicePort;
import com.plazoleta.plazoleta_service.domain.exception.InvalidDishFieldException;
import com.plazoleta.plazoleta_service.domain.exception.InvalidDishPriceException;
import com.plazoleta.plazoleta_service.domain.exception.InvalidTokenException;
import com.plazoleta.plazoleta_service.domain.exception.OwnerNotAuthorizedException;
import com.plazoleta.plazoleta_service.domain.model.Plato;
import com.plazoleta.plazoleta_service.domain.service.RegexValidationService;
import com.plazoleta.plazoleta_service.application.dto.DishCreateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DishUseCaseTest {
    @Mock
    private DishPersistencePort dishPersistencePort;
    @Mock
    private OwnerRestaurantQueryPort ownerRestaurantQueryPort;
    @Mock
    private TokenServicePort tokenServicePort;
    @Mock
    private RegexValidationService regexValidationService;
    @InjectMocks
    private DishUseCase dishUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dishUseCase = new DishUseCase(dishPersistencePort, ownerRestaurantQueryPort, tokenServicePort, regexValidationService);
    }

    @Test
    void createDish_success() {
        DishCreateRequestDto dto = new DishCreateRequestDto();
        dto.setNombre("Pizza");
        dto.setPrecio("12000");
        dto.setDescripcion("Pizza de queso");
        dto.setUrlImagen("http://img.com/pizza.jpg");
        dto.setCategoria("Italiana");
        dto.setRestauranteId(1L);
        String token = "Bearer validtoken";

        when(regexValidationService.isPositiveInteger("12000")).thenReturn(true);
        when(tokenServicePort.extractUserId(token)).thenReturn(10L);
        when(ownerRestaurantQueryPort.isOwnerOfRestaurant(10L, 1L)).thenReturn(true);
        Plato platoMock = new Plato();
        when(dishPersistencePort.saveDish(eq(1L), any(Plato.class))).thenReturn(platoMock);

        Plato result = dishUseCase.createDish(dto, token);
        assertNotNull(result);
        verify(dishPersistencePort).saveDish(eq(1L), any(Plato.class));
    }

    @Test
    void createDish_invalidName() {
        DishCreateRequestDto dto = new DishCreateRequestDto();
        dto.setNombre("");
        dto.setPrecio("12000");
        dto.setDescripcion("desc");
        dto.setUrlImagen("url");
        dto.setCategoria("cat");
        dto.setRestauranteId(1L);
        assertThrows(InvalidDishFieldException.class, () -> dishUseCase.createDish(dto, "token"));
    }

    @Test
    void createDish_invalidPrice() {
        DishCreateRequestDto dto = new DishCreateRequestDto();
        dto.setNombre("Pizza");
        dto.setPrecio("-5");
        dto.setDescripcion("desc");
        dto.setUrlImagen("url");
        dto.setCategoria("cat");
        dto.setRestauranteId(1L);
        when(regexValidationService.isPositiveInteger("-5")).thenReturn(false);
        assertThrows(InvalidDishPriceException.class, () -> dishUseCase.createDish(dto, "token"));
    }

    @Test
    void createDish_notOwner() {
        DishCreateRequestDto dto = new DishCreateRequestDto();
        dto.setNombre("Pizza");
        dto.setPrecio("12000");
        dto.setDescripcion("desc");
        dto.setUrlImagen("url");
        dto.setCategoria("cat");
        dto.setRestauranteId(1L);
        String token = "Bearer validtoken";
        when(regexValidationService.isPositiveInteger("12000")).thenReturn(true);
        when(tokenServicePort.extractUserId(token)).thenReturn(10L);
        when(ownerRestaurantQueryPort.isOwnerOfRestaurant(10L, 1L)).thenReturn(false);
        assertThrows(OwnerNotAuthorizedException.class, () -> dishUseCase.createDish(dto, token));
    }

    @Test
    void createDish_invalidToken() {
        DishCreateRequestDto dto = new DishCreateRequestDto();
        dto.setNombre("Pizza");
        dto.setPrecio("12000");
        dto.setDescripcion("desc");
        dto.setUrlImagen("url");
        dto.setCategoria("cat");
        dto.setRestauranteId(1L);
        String token = "Bearer invalid";
        when(regexValidationService.isPositiveInteger("12000")).thenReturn(true);
        when(tokenServicePort.extractUserId(token)).thenThrow(new InvalidTokenException("Invalid token"));
        assertThrows(InvalidTokenException.class, () -> dishUseCase.createDish(dto, token));
    }
}
