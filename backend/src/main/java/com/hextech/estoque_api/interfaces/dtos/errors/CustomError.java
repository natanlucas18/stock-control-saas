package com.hextech.estoque_api.interfaces.dtos.errors;

import java.time.Instant;

public record CustomError(String timestamp, Integer status, String message, String path) {
}
