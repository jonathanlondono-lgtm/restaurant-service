package com.plazoleta.plazoleta_service.application.port.out;

import java.util.List;

public interface UserClientPort {
    List<String> getUserRoleById(Long userId, String bearerToken);
}
