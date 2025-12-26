package com.hextech.estoque_api.interfaces.dtos.products;

import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.product.StockStatus;

import java.math.BigDecimal;

public class ProductResumeDTO {

    private Long id;
    private String code;
    private String name;
    private BigDecimal totalQuantity;
    private String unitMeasure;
    private StockStatus stockStatus;

    public ProductResumeDTO() {
    }

    public ProductResumeDTO(Product entity) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.name = entity.getName();
        this.totalQuantity = entity.getTotalQuantity();
        this.unitMeasure = entity.getUnitMeasure().toString();
        this.stockStatus = entity.getStockStatusEnum();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
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
