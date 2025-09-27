package com.hextech.estoque_api.interfaces.dtos.errors;

public record CustomError(String timestamp, Integer status, String message, String path) {
}
