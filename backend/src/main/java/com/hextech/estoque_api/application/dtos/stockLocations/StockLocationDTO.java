package com.hextech.estoque_api.application.dtos.stockLocations;

import com.hextech.estoque_api.domain.entities.StockLocation;

public record StockLocationDTO(Long id, String name) {

    public StockLocationDTO(StockLocation entity) {
        this(entity.getId(), entity.getName());
    }
}
