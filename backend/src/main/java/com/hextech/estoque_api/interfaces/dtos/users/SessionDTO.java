package com.hextech.estoque_api.interfaces.dtos.users;

import java.util.List;

public record SessionDTO(Long userId, String userName, List<String> userRoles , long tokenExpiresAt) {
}
