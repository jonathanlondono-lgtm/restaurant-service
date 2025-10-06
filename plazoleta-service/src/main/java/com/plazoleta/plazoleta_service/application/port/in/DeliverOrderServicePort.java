package com.plazoleta.plazoleta_service.application.port.in;

import com.plazoleta.plazoleta_service.application.dto.DeliverOrderRequestDto;

public interface DeliverOrderServicePort {
    void deliverOrder(DeliverOrderRequestDto request,  String bearerToken);
}
