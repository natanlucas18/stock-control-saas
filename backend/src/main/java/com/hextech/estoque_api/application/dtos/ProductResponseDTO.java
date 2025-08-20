package com.hextech.estoque_api.application.dtos;

import com.hextech.estoque_api.domain.entities.Product;

public class ProductResponseDTO {

    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private String unitMeasure;

    public ProductResponseDTO() {
    }

    public ProductResponseDTO(Long id, String name, Double price, Integer quantity, String unitMeasure) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.unitMeasure = unitMeasure;
    }

    public ProductResponseDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.quantity = entity.getQuantity();
        this.unitMeasure = entity.getUnitMeasure().toString();
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }
}
