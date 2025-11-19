package com.hextech.estoque_api.interfaces.dtos.stockLocations;

import com.hextech.estoque_api.domain.entities.stockLocation.StockLocation;

import java.math.BigDecimal;

public record StockQuantityDTO(Long id, String name, BigDecimal quantity) {

    public StockQuantityDTO(StockLocation entity, BigDecimal quantity) {
        this(entity.getId(), entity.getName(), quantity);
    }
}
