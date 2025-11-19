package com.hextech.estoque_api.interfaces.dtos.products;

import com.hextech.estoque_api.domain.entities.product.Product;
import com.hextech.estoque_api.domain.entities.product.StockStatus;
import com.hextech.estoque_api.interfaces.dtos.stockLocations.StockQuantityDTO;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductResponseDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private BigDecimal total_quantity;
    private BigDecimal stockMax;
    private BigDecimal stockMin;
    private String unitMeasure;
    private StockStatus stockStatus;
    private Set<StockQuantityDTO> stockLocations;

    public ProductResponseDTO() {
    }

    public ProductResponseDTO(Long id, String name, BigDecimal price, BigDecimal total_quantity, BigDecimal stockMax,
                              BigDecimal stockMin, String unitMeasure, StockStatus stockStatus) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.total_quantity = total_quantity;
        this.stockMax = stockMax;
        this.stockMin = stockMin;
        this.unitMeasure = unitMeasure;
        this.stockStatus = stockStatus;
    }

    public ProductResponseDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.total_quantity = entity.getTotalQuantity();
        this.stockMax = entity.getStockMax();
        this.stockMin = entity.getStockMin();
        this.unitMeasure = entity.getUnitMeasure().toString();
        this.stockLocations = entity.getStocks().stream().map(
                stockProduct -> new StockQuantityDTO(stockProduct.getStockLocation(), stockProduct.getQuantity())).collect(Collectors.toSet());
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

    public BigDecimal getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(BigDecimal total_quantity) {
        this.total_quantity = total_quantity;
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

    public Set<StockQuantityDTO> getStockLocations() {
        return stockLocations;
    }

    public void setStockLocations(Set<StockQuantityDTO> stockLocations) {
        this.stockLocations = stockLocations;
    }

    public StockStatus getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(StockStatus stockStatus) {
        this.stockStatus = stockStatus;
    }
}
