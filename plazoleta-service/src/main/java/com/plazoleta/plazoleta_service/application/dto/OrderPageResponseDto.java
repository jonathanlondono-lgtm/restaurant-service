package com.plazoleta.plazoleta_service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPageResponseDto {
    private List<OrderResponseDto> orders;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}


