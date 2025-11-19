package com.hextech.estoque_api.domain.entities.stockProduct;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class StockProductId {

    private Long stockLocationId;
    private Long productId;

    public StockProductId() {
    }

    public StockProductId(Long stockLocationId, Long productId) {
        this.stockLocationId = stockLocationId;
        this.productId = productId;
    }

    public Long getStockLocationId() {
        return stockLocationId;
    }

    public void setStockLocationId(Long stockLocationId) {
        this.stockLocationId = stockLocationId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StockProductId that = (StockProductId) o;
        return Objects.equals(stockLocationId, that.stockLocationId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockLocationId, productId);
    }
}
