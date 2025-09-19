package com.hextech.estoque_api.interfaces.dtos.products;

import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.product.StockStatus;

import java.math.BigDecimal;

public class ProductResponseDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal stockMax;
    private BigDecimal stockMin;
    private String unitMeasure;
    private StockStatus stockStatus;

    public ProductResponseDTO() {
    }

    public ProductResponseDTO(Long id, String name, BigDecimal price, BigDecimal quantity, BigDecimal stockMax, BigDecimal stockMin, String unitMeasure, StockStatus stockStatus) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.stockMax = stockMax;
        this.stockMin = stockMin;
        this.unitMeasure = unitMeasure;
        this.stockStatus = stockStatus;
    }

    public ProductResponseDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.quantity = entity.getQuantity();
        this.stockMax = entity.getStockMax();
        this.stockMin = entity.getStockMin();
        this.unitMeasure = entity.getUnitMeasure().toString();
        this.stockStatus = entity.checkStockStatus();
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getStockMax() {
        return stockMax;
    }

    public void setStockMax(BigDecimal stockMax) {
        this.stockMax = stockMax;
    }

    public BigDecimal getStockMin() {
        return stockMin;
    }

    public void setStockMin(BigDecimal stockMin) {
        this.stockMin = stockMin;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public StockStatus getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(StockStatus stockStatus) {
        this.stockStatus = stockStatus;
    }
}
