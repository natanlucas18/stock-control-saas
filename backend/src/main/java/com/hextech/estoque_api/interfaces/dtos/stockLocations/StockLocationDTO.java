package com.hextech.estoque_api.interfaces.dtos.stockLocations;

import com.hextech.estoque_api.domain.entities.stockLocation.StockLocation;
import jakarta.validation.constraints.NotBlank;

public record StockLocationDTO(Long id, @NotBlank(message = "Nome não pode ser nulo ou vazio.") String name) {

    public StockLocationDTO(StockLocation entity) {
        this(entity.getId(), entity.getName());
    }
}
