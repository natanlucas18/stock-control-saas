package com.hextech.estoque_api.interfaces.dtos.errors;

import java.time.Instant;

public record CustomError(Instant timestamp, Integer status, String message, String path) {
}
