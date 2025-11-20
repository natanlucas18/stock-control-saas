package com.hextech.estoque_api.interfaces.dtos.products;

import java.math.BigDecimal;

public class ProductRequestDTO {

    private String code;
    private String name;
    private BigDecimal price;
    private BigDecimal stockMax;
    private BigDecimal stockMin;
    private String unitMeasure;

    public ProductRequestDTO() {
    }

    public ProductRequestDTO(String code, String name, BigDecimal price, BigDecimal stockMax, BigDecimal stockMin, String unitMeasure) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stockMax = stockMax;
        this.stockMin = stockMin;
        this.unitMeasure = unitMeasure;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
}
