package com.hextech.estoque_api.application.dtos;

import com.hextech.estoque_api.domain.entities.Product;

public class ProductRequestDTO {

    private String name;
    private Double price;
    private String unitMeasure;
    private Long stockLocationId;

    public ProductRequestDTO() {
    }

    public ProductRequestDTO(String name, Double price, String unitMeasure, Long stockLocationId) {
        this.name = name;
        this.price = price;
        this.unitMeasure = unitMeasure;
        this.stockLocationId = stockLocationId;
    }

    public ProductRequestDTO(Product entity) {
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.unitMeasure = entity.getUnitMeasure().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public Long getStockLocationId() {
        return stockLocationId;
    }

    public void setStockLocationId(Long stockLocationId) {
        this.stockLocationId = stockLocationId;
    }
}
