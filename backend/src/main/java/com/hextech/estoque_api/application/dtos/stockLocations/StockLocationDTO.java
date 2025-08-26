package com.hextech.estoque_api.application.dtos.stockLocations;

import com.hextech.estoque_api.domain.entities.StockLocation;

public class StockLocationDTO {

    private Long id;
    private String name;

    public StockLocationDTO() {
    }

    public StockLocationDTO(String name) {
        this.name = name;
    }

    public StockLocationDTO(StockLocation entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
